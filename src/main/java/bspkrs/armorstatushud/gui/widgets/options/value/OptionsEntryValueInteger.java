package bspkrs.armorstatushud.gui.widgets.options.value;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;

public class OptionsEntryValueInteger extends OptionsEntryValueInput<Integer> {
    private OptionsEntryValueInteger(String optionName, Integer value, Consumer<Integer> save) {
        super(optionName, value, save);

        textField.setValidator((s) -> s.matches("^[0-9]*$"));
    }

    public OptionsEntryValueInteger(ForgeConfigSpec.IntValue spec) {
        this(getLangKey(spec), spec.get(), spec::set);
    }

    public void setValue(String text) {
        value = Integer.valueOf(text);
    }
}
