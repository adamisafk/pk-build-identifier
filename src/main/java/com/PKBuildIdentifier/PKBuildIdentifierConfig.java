package com.PKBuildIdentifier;

import com.PKBuildIdentifier.model.Builds;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

@ConfigGroup(PKBuildIdentifierConfig.CONFIG_GROUP)
public interface PKBuildIdentifierConfig extends Config
{
	String CONFIG_GROUP = "PKBuildIdentifier";
	String ENABLE_HELPER_PANEL = "enableHelperPanel";
	String ENABLE_MENU_ENTRY = "enableMenuEntry";
	String SELECT_CURRENT_BUILD = "selectCurrentBuild";
	String SHOW_BUILD_DESCRIPTION = "showBuildDescription";
	String SHOW_POSSIBLE_WEAPONS = "showPossibleWeapons";
	String SHOW_POSSIBLE_SPECS = "showPossibleSpecs";
	String SHOW_POSSIBLE_SPELLS = "showPossibleSpells";
	String SHOW_POSSIBLE_ARMOUR = "showPossibleArmour";
	String SHOW_POSSIBLE_PRAYERS = "showPossiblePrayers";
	String SHOW_TIPS = "showTips";


	/*
		SECTIONS
	 */

	// Panel
	@ConfigSection(
			position = 1,
			name = "Panel Settings",
			description = "Settings for the PK Build Helper panel."
	)
	String panelSection = "panelSection";

	// Menu Entry
	@ConfigSection(
			position = 2,
			name = "Menu Entry Settings",
			description = "Settings for menu entry."
	)
	String menuEntrySection = "menuEntrySection";

	// Misc
	@ConfigSection(
			position = 3,
			name = "Misc. Settings",
			description = "Settings for miscellaneous options."
	)
	String miscSection = "miscSection";

	/*
		ITEMS
	 */

	// Panel

	@ConfigItem(
			position = 1,
			keyName = ENABLE_HELPER_PANEL,
			name = "Enable Helper Panel",
			description = "When enabled, the PK Build Helper panel is displayed.",
			section = panelSection
	)
	default boolean enableHelperPanel()
	{
		return true;
	}

	@ConfigItem(
			position = 2,
			keyName = SHOW_BUILD_DESCRIPTION,
			name = "Show Build Description",
			description = "Shows short summary of selected PK build.",
			section = panelSection
	)
	default boolean showBuildDescription()
	{
		return true;
	}

	@ConfigItem(
			position = 3,
			keyName = SHOW_POSSIBLE_WEAPONS,
			name = "Show Possible Weapons",
			description = "Shows potential weapons ranked by likeliness.",
			section = panelSection
	)
	default boolean showPossibleWeapons()
	{
		return true;
	}

	@ConfigItem(
			position = 4,
			keyName = SHOW_POSSIBLE_SPECS,
			name = "Show Possible Specs",
			description = "Shows potential spec weapons ranked by likeliness.",
			section = panelSection
	)
	default boolean showPossibleSpecs()
	{
		return true;
	}

	@ConfigItem(
			position = 5,
			keyName = SHOW_POSSIBLE_SPELLS,
			name = "Show Possible Spells",
			description = "Shows potential spells ranked by likeliness.",
			section = panelSection
	)
	default boolean showPossibleSpells()
	{
		return true;
	}

	@ConfigItem(
			position = 6,
			keyName = SHOW_POSSIBLE_ARMOUR,
			name = "Show Possible Armour",
			description = "Shows potential armour switches ranked by likeliness.",
			section = panelSection
	)
	default boolean showPossibleArmour()
	{
		return true;
	}

	@ConfigItem(
			position = 7,
			keyName = SHOW_POSSIBLE_PRAYERS,
			name = "Show Possible Prayers",
			description = "Shows potential prayers used.",
			section = panelSection
	)
	default boolean showPossiblePrayers()
	{
		return true;
	}

	@ConfigItem(
			position = 8,
			keyName = SHOW_TIPS,
			name = "Show Tips",
			description = "Shows tips for fighting against selected build.",
			section = panelSection
	)
	default boolean showTips()
	{
		return true;
	}

	// Menu Entry

	@ConfigItem(
			position = 1,
			keyName = ENABLE_MENU_ENTRY,
			name = "Right-click 'View Build' Players",
			description = "When enabled, 'View Build' right-click option are shown on players.",
			section = menuEntrySection
	)
	default boolean enableMenuEntry()
	{
		return true;
	}

	// Misc

	@ConfigItem(
			position = 1,
			keyName = SELECT_CURRENT_BUILD,
			name = "Current Account Build",
			description = "Used to personalise tips in the PK Build Helper panel.",
			section = miscSection
	)
	default Builds selectCurrentBuild()
	{
		return Builds.NONE;
	}

}
