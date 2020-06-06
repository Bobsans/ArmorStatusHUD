package bspkrs.armorstatushud.gui.widgets;

import bspkrs.armorstatushud.gui.widgets.options.value.OptionsEntryValueInput;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;

import javax.annotation.ParametersAreNonnullByDefault;

public class WatchedTextField extends TextFieldWidget {
    private final OptionsEntryValueInput<?> watcher;

    public WatchedTextField(OptionsEntryValueInput<?> watcher, FontRenderer fontRenderer, int x, int y, int width, int height) {
        super(fontRenderer, x, y, width, height, "");
        this.watcher = watcher;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void writeText(String string) {
        super.writeText(string);
        watcher.setValue(getText());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void setText(String value) {
        super.setText(value);
        watcher.setValue(getText());
    }

    @Override
    public void deleteWords(int count) {
        super.deleteWords(count);
        watcher.setValue(getText());
    }
}
