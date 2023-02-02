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

public class ConfigScreen extends ConfigScreenBase {
    public ConfigScreen() {
        super(Reference.MODID);
    }

    protected OptionsListWidget fillOptions(OptionsListWidget widget) {
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.ENABLED));
        widget.addEntry(new OptionsEntryValueEnum<>(Reference.MODID, Config.Client.ALIGN_MODE, Config.AlignMode.values()));
        widget.addEntry(new OptionsEntryValueEnum<>(Reference.MODID, Config.Client.LIST_MODE, Config.ListMode.values()));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_ITEM_NAME));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_DAMAGE_OVERLAY));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_ITEM_COUNT));
        widget.addEntry(new OptionsEntryValueString(Reference.MODID, Config.Client.DAMAGE_COLOR_LIST));
        widget.addEntry(new OptionsEntryValueEnum<>(Reference.MODID, Config.Client.DAMAGE_DISPLAY_TYPE, Config.DamageDisplayType.values()));
        widget.addEntry(new OptionsEntryValueEnum<>(Reference.MODID, Config.Client.DAMAGE_THRESHOLD_TYPE, Config.DamageTresholdType.values()));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_ITEM_DAMAGE));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_ARMOR_DAMAGE));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_MAX_DAMAGE));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_EQUIPPED_ITEM));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_OFFHAND_ITEM));
        widget.addEntry(new OptionsEntryValueInteger(Reference.MODID, Config.Client.X_OFFSET));
        widget.addEntry(new OptionsEntryValueInteger(Reference.MODID, Config.Client.Y_OFFSET));
        widget.addEntry(new OptionsEntryValueInteger(Reference.MODID, Config.Client.Y_OFFSET_BOTTOM_CENTER));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.APPLY_X_OFFSET_TO_CENTER));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.APPLY_Y_OFFSET_TO_MIDDLE));
        widget.addEntry(new OptionsEntryValueBoolean(Reference.MODID, Config.Client.SHOW_IN_CHAT));

        return widget;
    }

    public static void open() {
        Minecraft.getInstance().setScreen(new ConfigScreen());
    }
}
