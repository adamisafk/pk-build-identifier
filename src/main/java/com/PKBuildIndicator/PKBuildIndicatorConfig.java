package com.PKBuildIndicator;

import com.PKBuildIndicator.model.Builds;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;

import java.awt.*;

@ConfigGroup(PKBuildIndicatorConfig.CONFIG_GROUP)
public interface PKBuildIndicatorConfig extends Config
{
	String CONFIG_GROUP = "PKBuildIndicator";
	String ENABLE_HELPER_PANEL = "enableHelperPanel";
	String ENABLE_BUILD_INDICATORS = "enableBuildIndicators";
	String ENABLE_MENU_ENTRY = "enableMenuEntry";
	String SELECT_CURRENT_BUILD = "selectCurrentBuild";
	String SHOW_INDICATORS_IN_PVP = "showIndicatorsInPVP";
	String PICK_INDICATOR_COLOUR = "pickIndicatorColour";
	String SHOW_BUILD_DESCRIPTION = "showBuildDescription";
	String SHOW_POSSIBLE_WEAPONS = "showPossibleWeapons";
	String SHOW_POSSIBLE_SPECS = "showPossibleSpecs";
	String SHOW_POSSIBLE_SPELLS = "showPossibleSpells";
	String SHOW_POSSIBLE_ARMOUR = "showPossibleArmour";
	String SHOW_POSSIBLE_PRAYERS = "showPossiblePrayers";
	String SHOW_TIPS = "showTips";
	String REPLACE_REPORT_ENTRY = "replaceReportEntry";
	String PICK_ENTRY_COLOUR = "pickEntryColour";


	/*
		SECTIONS
	 */

	// Indicators
	@ConfigSection(
			position = 1,
			name = "Indicator Settings",
			description = "Settings for player indicators."
	)
	String indicatorSection = "indicatorSection";

	// Panel
	@ConfigSection(
			position = 2,
			name = "Panel Settings",
			description = "Settings for the PK Build Helper panel."
	)
	String panelSection = "panelSection";

	// Menu Entry
	@ConfigSection(
			position = 3,
			name = "Menu Entry Settings",
			description = "Settings for menu entry."
	)
	String menuEntrySection = "menuEntrySection";

	// Misc
	@ConfigSection(
			position = 4,
			name = "Misc. Settings",
			description = "Settings for miscellaneous options."
	)
	String miscSection = "miscSection";

	/*
		ITEMS
	 */

	// Indicators

	@ConfigItem(
			position = 1,
			keyName = ENABLE_BUILD_INDICATORS,
			name = "Enable Build Indicators",
			description = "When enabled, build indicators are shown above players' heads.",
			section = indicatorSection
	)
	default boolean enableBuildIndicators()
	{
		return true;
	}

	@ConfigItem(
			position = 2,
			keyName = SHOW_INDICATORS_IN_PVP,
			name = "Only show in PVP worlds or wilderness",
			description = "Will only enable player indicators when in wilderness or on PVP worlds.",
			section = indicatorSection
	)
	default boolean showIndicatorsInPVP()
	{
		return false;
	}

	@ConfigItem(
			position = 3,
			keyName = PICK_INDICATOR_COLOUR,
			name = "Indicator Colour",
			description = "Colour of build indicators.",
			section = indicatorSection
	)
	default Color pickIndicatorColour()
	{
		return Color.RED;
	}

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
		return false;
	}

	@ConfigItem(
			position = 2,
			keyName = REPLACE_REPORT_ENTRY,
			name = "Replace 'Report' with 'View Build'",
			description = "When enabled, the 'Report' right-click option will be replaced with 'View Build'.",
			section = menuEntrySection
	)
	default boolean replaceReportEntry()
	{
		return false;
	}

	@ConfigItem(
			position = 3,
			keyName = PICK_ENTRY_COLOUR,
			name = "'View Build' Colour",
			description = "Select default colour for 'View Build' option.",
			section = menuEntrySection
	)
	Color pickEntryColour();

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
