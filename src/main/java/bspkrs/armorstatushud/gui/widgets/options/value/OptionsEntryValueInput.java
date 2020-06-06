package bspkrs.armorstatushud.gui.widgets.options.value;

import bspkrs.armorstatushud.gui.widgets.WatchedTextField;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.TextFieldWidget;

import java.util.function.Consumer;

public abstract class OptionsEntryValueInput<T> extends OptionsEntryValue<T> {
    final TextFieldWidget textField;

    OptionsEntryValueInput(String optionName, T value, Consumer<T> save) {
        super(optionName, value, save);

        textField = new WatchedTextField(this, client.fontRenderer, 0, 0, 138, 18);
        textField.setMaxStringLength(256);
        textField.setText(String.valueOf(value));
    }

    @Override
    protected void drawValue(int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        textField.x = x + entryWidth - 160;
        textField.y = y + entryHeight / 6;
        textField.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public IGuiEventListener getListener() {
        return textField;
    }

    public abstract void setValue(String value);
}
