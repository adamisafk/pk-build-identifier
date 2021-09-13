package com.PKBuildIndicator;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PKBuildIndicatorPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PKBuildIndicatorPlugin.class);
		RuneLite.main(args);
	}
}