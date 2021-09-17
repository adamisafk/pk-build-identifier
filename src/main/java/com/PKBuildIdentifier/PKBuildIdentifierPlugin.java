package com.PKBuildIdentifier;

import com.PKBuildIdentifier.ui.PKBuildPanel;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ObjectArrays;
import com.google.inject.Provides;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.*;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.menus.MenuManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.Text;
import net.runelite.http.api.hiscore.HiscoreEndpoint;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.EnumSet;

@Slf4j
@PluginDescriptor(
	name = "PK Build Identifier",
	description = "Identifies players' account builds for PKing",
	tags = {"builds", "players", "pking", "pk", "player", "killing", "pvp", "identify", "identifier", "helper" }
)
public class PKBuildIdentifierPlugin extends Plugin
{
	// Constants
	private static final String MENU_ENTRY_TEXT = "View Build";
	private static final String DELETE_OPTION = "Delete";
	private static final String KICK_OPTION = "Kick";

	private static final ImmutableSet<String> AFTER_OPTIONS =
			ImmutableSet.of("Message", "Add ignore", "Remove friend", DELETE_OPTION, KICK_OPTION);

	private static final ImmutableSet<MenuAction> PLAYER_MENU_ACTIONS = ImmutableSet.of(
			MenuAction.PLAYER_FIRST_OPTION, MenuAction.PLAYER_SECOND_OPTION, MenuAction.PLAYER_THIRD_OPTION, MenuAction.PLAYER_FOURTH_OPTION,
			MenuAction.PLAYER_FIFTH_OPTION, MenuAction.PLAYER_SIXTH_OPTION, MenuAction.PLAYER_SEVENTH_OPTION, MenuAction.PLAYER_EIGTH_OPTION
	);

	@Inject
	private ClientToolbar clientToolbar;

	@Inject
	private MenuManager menuManager;

	@Inject
	private Client client;

	@Inject
	private PKBuildIdentifierConfig config;

	private NavigationButton navButton;
	private PKBuildPanel pkBuildPanel;

	@Override
	protected void startUp() throws Exception
	{
		log.info("PK Build Identifier started!");

		if(client != null) {
			// Side Panel Config
			if(config.enableHelperPanel()) {
				pkBuildPanel = injector.getInstance(PKBuildPanel.class);

				final BufferedImage icon = ImageIO.read(new File("icon.png"));

				navButton = NavigationButton.builder()
						.tooltip("PK Build Identifier")
						.icon(icon)
						.priority(1)
						.panel(pkBuildPanel)
						.build();
				clientToolbar.addNavigation(navButton);
			}

			// Menu Entry Config
			if(config.enableMenuEntry()) {
				menuManager.addPlayerMenuItem(MENU_ENTRY_TEXT);
			}
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("PK Build Identifier stopped!");

		if(client != null) {
			menuManager.removePlayerMenuItem(MENU_ENTRY_TEXT);
		}
		// Remove Panel
		pkBuildPanel.shutdown();
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged event) {
		// Return null if config is not PK Build Identifier
		if (!event.getGroup().equals(PKBuildIdentifierConfig.CONFIG_GROUP) || event.getKey() == null)
		{
			return;
		}

		// Handle config change
		switch(event.getKey())
		{
			case PKBuildIdentifierConfig.ENABLE_HELPER_PANEL:
				pkBuildPanel.shutdown();
				clientToolbar.removeNavigation(navButton);
				if (config.enableHelperPanel()) {
					clientToolbar.addNavigation(navButton);
				}
			case PKBuildIdentifierConfig.ENABLE_MENU_ENTRY:
				if(client != null) {
					menuManager.removePlayerMenuItem(MENU_ENTRY_TEXT);
					if (config.enableMenuEntry()) {
						menuManager.addPlayerMenuItem(MENU_ENTRY_TEXT);
					}
				}

		}
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event) {
		if(!config.enableMenuEntry()) {
			return;
		}
		final int componentId = event.getActionParam1();
		int groupId = WidgetInfo.TO_GROUP(componentId);
		String option = event.getOption();

		if (
				groupId == WidgetInfo.FRIENDS_LIST.getGroupId() ||
				groupId == WidgetInfo.FRIENDS_CHAT.getGroupId() ||
				groupId == WidgetInfo.CHATBOX.getGroupId() && !KICK_OPTION.equals(option) ||
				groupId == WidgetInfo.RAIDING_PARTY.getGroupId() ||
				groupId == WidgetInfo.PRIVATE_CHAT_MESSAGE.getGroupId() ||
				groupId == WidgetInfo.IGNORE_LIST.getGroupId() ||
				componentId == WidgetInfo.CLAN_MEMBER_LIST.getId() ||
				componentId == WidgetInfo.CLAN_GUEST_MEMBER_LIST.getId()
		) {
			if (
				!AFTER_OPTIONS.contains(option) ||
				(option.equals(DELETE_OPTION) && groupId != WidgetInfo.IGNORE_LIST.getGroupId())
			) {
				return;
			}

			final MenuEntry viewBuild = new MenuEntry();
			viewBuild.setTarget(event.getTarget());
			viewBuild.setType(MenuAction.RUNELITE.getId());
			viewBuild.setParam0(event.getActionParam0());
			viewBuild.setParam1(event.getActionParam1());
			viewBuild.setIdentifier(event.getIdentifier());

			insertMenuEntry(viewBuild, client.getMenuEntries());
		}
	}

	@Subscribe
	private void onMenuOptionClicked(MenuOptionClicked event) {
		if(event.getMenuAction() == MenuAction.RUNELITE || event.getMenuAction() == MenuAction.RUNELITE_PLAYER) {
			log.info("View Build/Report/Lookup Clicked!");
			final String target;
			HiscoreEndpoint endpoint = HiscoreEndpoint.NORMAL;

			if(event.getMenuAction() == MenuAction.RUNELITE_PLAYER || PLAYER_MENU_ACTIONS.contains(event.getMenuAction())) {
				Player player = client.getCachedPlayers()[event.getId()];
				if(player == null) {
					return;
				}

				target = player.getName();
			} else {
				endpoint = findHiscoreEndpointFromPlayerName(event.getMenuTarget());
				target = event.getMenuTarget();
			}

			if (target != null) {
				String targetNoTags = Text.removeTags(target);
				lookupPlayer(targetNoTags, endpoint);
			}
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "PK Build Identifier is enabled!", null);
		}
	}

	// Helper Functions
	private void insertMenuEntry(MenuEntry newEntry, MenuEntry[] entries)
	{
		MenuEntry[] newMenu = ObjectArrays.concat(entries, newEntry);
		int menuEntryCount = newMenu.length;
		ArrayUtils.swap(newMenu, menuEntryCount - 1, menuEntryCount - 2);
		client.setMenuEntries(newMenu);
	}

	private HiscoreEndpoint findHiscoreEndpointFromPlayerName(String name) {
		if (name.contains(IconID.IRONMAN.toString()))
		{
			return HiscoreEndpoint.IRONMAN;
		}
		if (name.contains(IconID.ULTIMATE_IRONMAN.toString()))
		{
			return HiscoreEndpoint.ULTIMATE_IRONMAN;
		}
		if (name.contains(IconID.HARDCORE_IRONMAN.toString()))
		{
			return HiscoreEndpoint.HARDCORE_IRONMAN;
		}
		if (name.contains(IconID.LEAGUE.toString()))
		{
			return HiscoreEndpoint.LEAGUE;
		}
		return HiscoreEndpoint.NORMAL;
	}

	private void lookupPlayer(String playerName, HiscoreEndpoint endpoint) {
		SwingUtilities.invokeLater(() -> {
			if(!navButton.isSelected()) {
				navButton.getOnSelect().run();
			}
			pkBuildPanel.lookup(playerName, endpoint);
		});
	}

	public HiscoreEndpoint getWorldEndpoint()
	{
		if (client != null)
		{
			EnumSet<WorldType> wTypes = client.getWorldType();

			if (wTypes.contains(WorldType.SEASONAL))
			{
				return HiscoreEndpoint.TOURNAMENT;
			}
			else if (wTypes.contains(WorldType.DEADMAN))
			{
				return HiscoreEndpoint.DEADMAN;
			}
		}
		return HiscoreEndpoint.NORMAL;
	}

	@Provides
	PKBuildIdentifierConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PKBuildIdentifierConfig.class);
	}
}
