package bspkrs.armorstatushud.gui.widgets.options.value;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;

public class OptionsEntryValueString extends OptionsEntryValueInput<String> {
    private OptionsEntryValueString(String optionName, String value, Consumer<String> save) {
        super(optionName, value, save);
    }

    public OptionsEntryValueString(ForgeConfigSpec.ConfigValue<String> spec) {
        this(getLangKey(spec), spec.get(), spec::set);
    }

    public void setValue(String text) {
        value = text;
    }
}
