package com.PKBuildIdentifier;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PKBuildIdentifierPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PKBuildIdentifierPlugin.class);
		RuneLite.main(args);
	}
}