package bspkrs.armorstatushud.gui;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.config.Config;
import by.bobsans.boblib.gui.screens.ConfigScreenBase;
import by.bobsans.boblib.gui.widgets.OptionsListWidget;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueBoolean;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueEnum;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueInteger;
import by.bobsans.boblib.gui.widgets.value.OptionsEntryValueString;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

public class ConfigGUI extends ConfigScreenBase {
    public ConfigGUI(Screen parent) {
        super(parent, Reference.MODID, null, null);
    }

    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new ConfigGUI(null));
    }

    protected OptionsListWidget fillOptions(OptionsListWidget widget) {
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.ENABLED));
        widget.add(new OptionsEntryValueEnum<>(Reference.MODID, Config.GENERAL.ALIGN_MODE, Config.AlignMode.values()));
        widget.add(new OptionsEntryValueEnum<>(Reference.MODID, Config.GENERAL.LIST_MODE, Config.ListMode.values()));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.ENABLE_ITEM_NAME));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_DAMAGE_OVERLAY));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_ITEM_COUNT));
        widget.add(new OptionsEntryValueString(Reference.MODID, Config.GENERAL.DAMAGE_COLOR_LIST));
        widget.add(new OptionsEntryValueEnum<>(Reference.MODID, Config.GENERAL.DAMAGE_DISPLAY_TYPE, Config.DamageDisplayType.values()));
        widget.add(new OptionsEntryValueEnum<>(Reference.MODID, Config.GENERAL.DAMAGE_THRESHOLD_TYPE, Config.DamageTresholdType.values()));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_ITEM_DAMAGE));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_ARMOR_DAMAGE));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_MAX_DAMAGE));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_EQUIPPED_ITEM));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_OFFHAND_ITEM));
        widget.add(new OptionsEntryValueInteger(Reference.MODID, Config.GENERAL.X_OFFSET));
        widget.add(new OptionsEntryValueInteger(Reference.MODID, Config.GENERAL.Y_OFFSET));
        widget.add(new OptionsEntryValueInteger(Reference.MODID, Config.GENERAL.Y_OFFSET_BOTTOM_CENTER));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.APPLY_X_OFFSET_TO_CENTER));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.APPLY_Y_OFFSET_TO_MIDDLE));
        widget.add(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_IN_CHAT));

        return widget;
    }
}
