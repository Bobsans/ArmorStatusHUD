package bspkrs.armorstatushud.config;

import bspkrs.armorstatushud.ArmorStatusHUD;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class Config {
    private static final String CATEGORY_GENERAL = "general";

    public static final String DEFAULT_COLOR_LIST = "100,f; 80,7; 60,e; 40,6; 25,c; 10,4";

    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec CLIENT_CONFIG;

    public static final ForgeConfigSpec.BooleanValue ENABLED;
    public static final ForgeConfigSpec.EnumValue<AlignMode> ALIGN_MODE;
    public static final ForgeConfigSpec.EnumValue<ListMode> LIST_MODE;
    public static final ForgeConfigSpec.BooleanValue ENABLE_ITEM_NAME;
    public static final ForgeConfigSpec.BooleanValue SHOW_DAMAGE_OVERLAY;
    public static final ForgeConfigSpec.BooleanValue SHOW_ITEM_COUNT;
    public static final ForgeConfigSpec.ConfigValue<String> DAMAGE_COLOR_LIST;
    public static final ForgeConfigSpec.EnumValue<DamageDisplayType> DAMAGE_DISPLAY_TYPE;
    public static final ForgeConfigSpec.EnumValue<DamageTresholdType> DAMAGE_THRESHOLD_TYPE;
    public static final ForgeConfigSpec.BooleanValue SHOW_ITEM_DAMAGE;
    public static final ForgeConfigSpec.BooleanValue SHOW_ARMOR_DAMAGE;
    public static final ForgeConfigSpec.BooleanValue SHOW_MAX_DAMAGE;
    public static final ForgeConfigSpec.BooleanValue SHOW_EQUIPPED_ITEM;
    public static final ForgeConfigSpec.BooleanValue SHOW_OFFHAND_ITEM;
    public static final ForgeConfigSpec.IntValue X_OFFSET;
    public static final ForgeConfigSpec.IntValue Y_OFFSET;
    public static final ForgeConfigSpec.IntValue Y_OFFSET_BOTTOM_CENTER;
    public static final ForgeConfigSpec.BooleanValue APPLY_X_OFFSET_TO_CENTER;
    public static final ForgeConfigSpec.BooleanValue APPLY_Y_OFFSET_TO_MIDDLE;
    public static final ForgeConfigSpec.BooleanValue SHOW_IN_CHAT;

    static {
        CLIENT_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        ENABLED = CLIENT_BUILDER.comment("Enables or disables the Armor Status HUD display").translation("armorstatushud.config.client.enabled").define("enabled", true);
        ALIGN_MODE = CLIENT_BUILDER.comment("Sets the position of the HUD on the screen").translation("armorstatushud.config.client.alignMode").defineEnum("alignMode", AlignMode.BOTTOMLEFT);
        LIST_MODE = CLIENT_BUILDER.comment("Sets the direction to display status items").translation("armorstatushud.config.client.listMode").defineEnum("listMode", ListMode.HORIZONTAL);
        ENABLE_ITEM_NAME = CLIENT_BUILDER.comment("Set to true to show item names").translation("armorstatushud.config.client.enableItemName").define("enableItemName", false);
        SHOW_DAMAGE_OVERLAY = CLIENT_BUILDER.comment("Set to true to show the standard inventory item overlay (damage bar)").translation("armorstatushud.config.client.showDamageOverlay").define("showDamageOverlay", true);
        SHOW_ITEM_COUNT = CLIENT_BUILDER.comment("Set to true to show the item count overlay").translation("armorstatushud.config.client.showItemCount").define("showItemCount", true);
        DAMAGE_COLOR_LIST = CLIENT_BUILDER.comment("This is a list of percent damage thresholds and text color codes that will be used when item damage is <= the threshold. Format used: \",\" separates the threshold and the color code, \";\" separates each pair. Valid color values are 0-9, a-f (color values can be found here: http://www.minecraftwiki.net/wiki/File:Colors.png)").translation("armorstatushud.config.client.damageColorList").define("damageColorList", DEFAULT_COLOR_LIST);
        DAMAGE_DISPLAY_TYPE = CLIENT_BUILDER.comment("Valid damageDisplayType strings are value, percent, or none").translation("armorstatushud.config.client.damageDisplayType").defineEnum("damageDisplayType", DamageDisplayType.VALUE);
        DAMAGE_THRESHOLD_TYPE = CLIENT_BUILDER.comment("The type of threshold to use when applying the damageColorList thresholds").translation("armorstatushud.config.client.damageThresholdType").defineEnum("damageThresholdType", DamageTresholdType.PERCENT);
        SHOW_ITEM_DAMAGE = CLIENT_BUILDER.comment("Set to true to show held item damage values").translation("armorstatushud.config.client.showItemDamage").define("showItemDamage", true);
        SHOW_ARMOR_DAMAGE = CLIENT_BUILDER.comment("Set to true to show armor damage values").translation("armorstatushud.config.client.showArmorDamage").define("showArmorDamage", true);
        SHOW_MAX_DAMAGE = CLIENT_BUILDER.comment("Set to true to show the max damage when damageDisplayType=value").translation("armorstatushud.config.client.showMaxDamage").define("showMaxDamage", false);
        SHOW_EQUIPPED_ITEM = CLIENT_BUILDER.comment("Set to true to show info for your currently equipped item").translation("armorstatushud.config.client.showEquippedItem").define("showEquippedItem", true);
        SHOW_OFFHAND_ITEM = CLIENT_BUILDER.comment("Set to true to show info for your currently offhand equipped item").translation("armorstatushud.config.client.showOffHandItem").define("showOffHandItem", false);
        X_OFFSET = CLIENT_BUILDER.comment("Horizontal offset from the edge of the screen (when using right alignments the x offset is relative to the right edge of the screen)").translation("armorstatushud.config.client.xOffset").defineInRange("xOffset", 2, 0, Integer.MAX_VALUE);
        Y_OFFSET = CLIENT_BUILDER.comment("Vertical offset from the edge of the screen (when using bottom alignments the y offset is relative to the bottom edge of the screen)").translation("armorstatushud.config.client.yOffset").defineInRange("yOffset", 2, 0, Integer.MAX_VALUE);
        Y_OFFSET_BOTTOM_CENTER = CLIENT_BUILDER.comment("Vertical offset used only for the bottomcenter alignment to avoid the vanilla HUD").translation("armorstatushud.config.client.yOffsetBottomCenter").defineInRange("yOffsetBottomCenter", 41, 0, Integer.MAX_VALUE);
        APPLY_X_OFFSET_TO_CENTER = CLIENT_BUILDER.comment("Set to true if you want the xOffset value to be applied when using a center alignment").translation("armorstatushud.config.client.applyXOffsetToCenter").define("applyXOffsetToCenter", false);
        APPLY_Y_OFFSET_TO_MIDDLE = CLIENT_BUILDER.comment("Set to true if you want the yOffset value to be applied when using a middle alignment").translation("armorstatushud.config.client.applyYOffsetToMiddle").define("applyYOffsetToMiddle", false);
        SHOW_IN_CHAT = CLIENT_BUILDER.comment("Set to true to show info when chat is open, false to disable info when chat is open").translation("armorstatushud.config.client.showInChat").define("showInChat", false);

        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void loadConfig(Path path) {
        ArmorStatusHUD.LOGGER.debug("Loading Armor Status HUD config...");

        CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().autoreload().writingMode(WritingMode.REPLACE).build();
        configData.load();
        Config.CLIENT_CONFIG.setConfig(configData);
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG);

        loadConfig(FMLPaths.CONFIGDIR.get().resolve("armorstatushud-client.toml"));
    }

    public enum AlignMode {
        TOPLEFT, TOPCENTER, TOPRIGHT, MIDDLELEFT, MIDDLECENTER, MIDDLERIGHT, BOTTOMLEFT, BOTTOMCENTER, BOTTOMRIGHT
    }

    public enum ListMode {
        VERTICAL, HORIZONTAL
    }

    public enum DamageDisplayType {
        VALUE, PERCENT, NONE
    }

    public enum DamageTresholdType {
        VALUE, PERCENT
    }
}
