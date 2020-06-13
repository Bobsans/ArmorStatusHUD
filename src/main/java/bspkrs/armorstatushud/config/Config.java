package bspkrs.armorstatushud.config;

import bspkrs.armorstatushud.Reference;
import by.bobsans.boblib.config.ConfigBase;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class Config extends ConfigBase {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);
    public static final ForgeConfigSpec spec = BUILDER.build();

    public static final String DEFAULT_COLOR_LIST = "100,f; 80,7; 60,e; 40,6; 25,c; 10,4";

    public static class General {
        public final ForgeConfigSpec.BooleanValue ENABLED;
        public final ForgeConfigSpec.EnumValue<AlignMode> ALIGN_MODE;
        public final ForgeConfigSpec.EnumValue<ListMode> LIST_MODE;
        public final ForgeConfigSpec.BooleanValue ENABLE_ITEM_NAME;
        public final ForgeConfigSpec.BooleanValue SHOW_DAMAGE_OVERLAY;
        public final ForgeConfigSpec.BooleanValue SHOW_ITEM_COUNT;
        public final ForgeConfigSpec.ConfigValue<String> DAMAGE_COLOR_LIST;
        public final ForgeConfigSpec.EnumValue<DamageDisplayType> DAMAGE_DISPLAY_TYPE;
        public final ForgeConfigSpec.EnumValue<DamageTresholdType> DAMAGE_THRESHOLD_TYPE;
        public final ForgeConfigSpec.BooleanValue SHOW_ITEM_DAMAGE;
        public final ForgeConfigSpec.BooleanValue SHOW_ARMOR_DAMAGE;
        public final ForgeConfigSpec.BooleanValue SHOW_MAX_DAMAGE;
        public final ForgeConfigSpec.BooleanValue SHOW_EQUIPPED_ITEM;
        public final ForgeConfigSpec.BooleanValue SHOW_OFFHAND_ITEM;
        public final ForgeConfigSpec.IntValue X_OFFSET;
        public final ForgeConfigSpec.IntValue Y_OFFSET;
        public final ForgeConfigSpec.IntValue Y_OFFSET_BOTTOM_CENTER;
        public final ForgeConfigSpec.BooleanValue APPLY_X_OFFSET_TO_CENTER;
        public final ForgeConfigSpec.BooleanValue APPLY_Y_OFFSET_TO_MIDDLE;
        public final ForgeConfigSpec.BooleanValue SHOW_IN_CHAT;

        public General(ForgeConfigSpec.Builder builder) {
            builder.comment("General settings").push("general");

            ENABLED = builder.comment("Enables or disables the Armor Status HUD display").translation("armorstatushud.config.client.enabled").define("enabled", true);
            ALIGN_MODE = builder.comment("Sets the position of the HUD on the screen").translation("armorstatushud.config.client.alignMode").defineEnum("alignMode", AlignMode.BOTTOMLEFT);
            LIST_MODE = builder.comment("Sets the direction to display status items").translation("armorstatushud.config.client.listMode").defineEnum("listMode", ListMode.HORIZONTAL);
            ENABLE_ITEM_NAME = builder.comment("Set to true to show item names").translation("armorstatushud.config.client.enableItemName").define("enableItemName", false);
            SHOW_DAMAGE_OVERLAY = builder.comment("Set to true to show the standard inventory item overlay (damage bar)").translation("armorstatushud.config.client.showDamageOverlay").define("showDamageOverlay", true);
            SHOW_ITEM_COUNT = builder.comment("Set to true to show the item count overlay").translation("armorstatushud.config.client.showItemCount").define("showItemCount", true);
            DAMAGE_COLOR_LIST = builder.comment("This is a list of percent damage thresholds and text color codes that will be used when item damage is <= the threshold. Format used: \",\" separates the threshold and the color code, \";\" separates each pair. Valid color values are 0-9, a-f (color values can be found here: http://www.minecraftwiki.net/wiki/File:Colors.png)").translation("armorstatushud.config.client.damageColorList").define("damageColorList", DEFAULT_COLOR_LIST);
            DAMAGE_DISPLAY_TYPE = builder.comment("Valid damageDisplayType strings are value, percent, or none").translation("armorstatushud.config.client.damageDisplayType").defineEnum("damageDisplayType", DamageDisplayType.VALUE);
            DAMAGE_THRESHOLD_TYPE = builder.comment("The type of threshold to use when applying the damageColorList thresholds").translation("armorstatushud.config.client.damageThresholdType").defineEnum("damageThresholdType", DamageTresholdType.PERCENT);
            SHOW_ITEM_DAMAGE = builder.comment("Set to true to show held item damage values").translation("armorstatushud.config.client.showItemDamage").define("showItemDamage", true);
            SHOW_ARMOR_DAMAGE = builder.comment("Set to true to show armor damage values").translation("armorstatushud.config.client.showArmorDamage").define("showArmorDamage", true);
            SHOW_MAX_DAMAGE = builder.comment("Set to true to show the max damage when damageDisplayType=value").translation("armorstatushud.config.client.showMaxDamage").define("showMaxDamage", false);
            SHOW_EQUIPPED_ITEM = builder.comment("Set to true to show info for your currently equipped item").translation("armorstatushud.config.client.showEquippedItem").define("showEquippedItem", true);
            SHOW_OFFHAND_ITEM = builder.comment("Set to true to show info for your currently offhand equipped item").translation("armorstatushud.config.client.showOffHandItem").define("showOffHandItem", false);
            X_OFFSET = builder.comment("Horizontal offset from the edge of the screen (when using right alignments the x offset is relative to the right edge of the screen)").translation("armorstatushud.config.client.xOffset").defineInRange("xOffset", 2, 0, Integer.MAX_VALUE);
            Y_OFFSET = builder.comment("Vertical offset from the edge of the screen (when using bottom alignments the y offset is relative to the bottom edge of the screen)").translation("armorstatushud.config.client.yOffset").defineInRange("yOffset", 2, 0, Integer.MAX_VALUE);
            Y_OFFSET_BOTTOM_CENTER = builder.comment("Vertical offset used only for the bottomcenter alignment to avoid the vanilla HUD").translation("armorstatushud.config.client.yOffsetBottomCenter").defineInRange("yOffsetBottomCenter", 41, 0, Integer.MAX_VALUE);
            APPLY_X_OFFSET_TO_CENTER = builder.comment("Set to true if you want the xOffset value to be applied when using a center alignment").translation("armorstatushud.config.client.applyXOffsetToCenter").define("applyXOffsetToCenter", false);
            APPLY_Y_OFFSET_TO_MIDDLE = builder.comment("Set to true if you want the yOffset value to be applied when using a middle alignment").translation("armorstatushud.config.client.applyYOffsetToMiddle").define("applyYOffsetToMiddle", false);
            SHOW_IN_CHAT = builder.comment("Set to true to show info when chat is open, false to disable info when chat is open").translation("armorstatushud.config.client.showInChat").define("showInChat", false);

            builder.pop();
        }
    }

    public static void register(final ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, spec);

        loadConfig(spec, FMLPaths.CONFIGDIR.get().resolve(Reference.MODID + "-client.toml"));
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
