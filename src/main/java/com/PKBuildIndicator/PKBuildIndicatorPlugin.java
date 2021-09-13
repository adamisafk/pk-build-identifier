package com.PKBuildIndicator;

import com.PKBuildIndicator.ui.PKBuildPanel;
import com.google.inject.Provides;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.io.File;

@Slf4j
@PluginDescriptor(
	name = "PK Build Indicators",
	description = "Highlight players' account builds on-screen for PKing",
	tags = {"builds", "highlight", "overlay", "players", "pking", "player", "killing" }
)
public class PKBuildIndicatorPlugin extends Plugin
{
	@Inject
	private ClientToolbar clientToolbar;

	private NavigationButton navButton;

	@Inject
	private Client client;

	@Inject
	private PKBuildIndicatorConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("PK Build Indicator started!");

		// Add Panel
		if(config.enableHelperPanel()) {
			final PKBuildPanel panel = injector.getInstance(PKBuildPanel.class);
			panel.init();

			final BufferedImage icon = ImageIO.read(new File("icon.png"));

			navButton = NavigationButton.builder()
					.tooltip("PK Builds Helper")
					.icon(icon)
					.priority(1)
					.panel(panel)
					.build();
			clientToolbar.addNavigation(navButton);
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("PK Build Indicator stopped!");

		// Remove Panel
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged event) {
		// Return null if config is not PK Build Indicator
		if (!event.getGroup().equals(PKBuildIndicatorConfig.CONFIG_GROUP) || event.getKey() == null)
		{
			return;
		}

		// Handle config change
		switch(event.getKey())
		{
			case PKBuildIndicatorConfig.ENABLE_HELPER_PANEL:
				clientToolbar.removeNavigation(navButton);
				if (config.enableHelperPanel()) {
					clientToolbar.addNavigation(navButton);
				}
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "PK Build Indicator is enabled!", null);
		}
	}

	@Provides
	PKBuildIndicatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PKBuildIndicatorConfig.class);
	}
}
