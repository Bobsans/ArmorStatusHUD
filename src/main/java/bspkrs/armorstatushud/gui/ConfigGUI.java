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

public class ConfigGUI extends ConfigScreenBase {
    public ConfigGUI() {
        super(Reference.MODID, null, null);
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new ConfigGUI());
    }

    protected OptionsListWidget fillOptions(OptionsListWidget widget) {
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.ENABLED));
        widget.addEntry(new OptionsEntryValueEnum<>(Reference.MODID, Config.GENERAL.ALIGN_MODE, Config.AlignMode.values()));
        widget.addEntry(new OptionsEntryValueEnum<>(Reference.MODID, Config.GENERAL.LIST_MODE, Config.ListMode.values()));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.ENABLE_ITEM_NAME));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_DAMAGE_OVERLAY));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_ITEM_COUNT));
        widget.addEntry(new OptionsEntryValueString(Reference.MODID, Config.GENERAL.DAMAGE_COLOR_LIST));
        widget.addEntry(new OptionsEntryValueEnum<>(Reference.MODID, Config.GENERAL.DAMAGE_DISPLAY_TYPE, Config.DamageDisplayType.values()));
        widget.addEntry(new OptionsEntryValueEnum<>(Reference.MODID, Config.GENERAL.DAMAGE_THRESHOLD_TYPE, Config.DamageTresholdType.values()));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_ITEM_DAMAGE));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_ARMOR_DAMAGE));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_MAX_DAMAGE));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_EQUIPPED_ITEM));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_OFFHAND_ITEM));
        widget.addEntry(new OptionsEntryValueInteger(Reference.MODID, Config.GENERAL.X_OFFSET));
        widget.addEntry(new OptionsEntryValueInteger(Reference.MODID, Config.GENERAL.Y_OFFSET));
        widget.addEntry(new OptionsEntryValueInteger(Reference.MODID, Config.GENERAL.Y_OFFSET_BOTTOM_CENTER));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.APPLY_X_OFFSET_TO_CENTER));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.APPLY_Y_OFFSET_TO_MIDDLE));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.GENERAL.SHOW_IN_CHAT));

        return widget;
    }
}
