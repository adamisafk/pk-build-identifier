package com.PKBuildIndicator;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "PK Build Indicators",
	description = "Highlight players' account builds on-screen for PKing",
	tags = {"builds", "highlight", "overlay", "players", "pking", "player", "killing" }
)
public class PKBuildIndicatorPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private PKBuildIndicatorConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("PK Build Indicator started!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("PK Build Indicator stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "PK Build Indicator says " + config.greeting(), null);
		}
	}

	@Provides
	PKBuildIndicatorConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PKBuildIndicatorConfig.class);
	}
}
