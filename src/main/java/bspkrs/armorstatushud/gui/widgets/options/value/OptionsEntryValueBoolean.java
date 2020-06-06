package bspkrs.armorstatushud.gui.widgets.options.value;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;

public class OptionsEntryValueBoolean extends OptionsEntryValue<Boolean> {
    private final Button button;

    private OptionsEntryValueBoolean(String optionName, boolean value, Consumer<Boolean> save) {
        super(optionName, value, save);

        this.button = new Button(0, 0, 140, 20, I18n.format("gui." + (value ? "yes" : "no")), (btn) -> this.value = !this.value);
    }

    public OptionsEntryValueBoolean(ForgeConfigSpec.BooleanValue spec) {
        this(getLangKey(spec), spec.get(), spec::set);
    }

    @Override
    protected void drawValue(int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks) {
        this.button.x = entryWidth - 160;
        this.button.y = y + entryHeight / 6;
        this.button.setMessage(I18n.format("gui." + (value ? "yes" : "no")));
        this.button.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public IGuiEventListener getListener() {
        return button;
    }
}
