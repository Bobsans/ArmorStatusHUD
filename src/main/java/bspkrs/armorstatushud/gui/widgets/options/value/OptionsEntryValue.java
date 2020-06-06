package bspkrs.armorstatushud.gui.widgets.options.value;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.gui.widgets.options.OptionsListWidget;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Consumer;

public abstract class OptionsEntryValue<T> extends OptionsListWidget.Entry {
    private final Consumer<T> save;
    private final TextComponent title;
    private final String description;
    T value;
    private int x;

    OptionsEntryValue(String optionName, T value, Consumer<T> save) {
        this.title = new TranslationTextComponent(optionName);
        this.description = optionName + ".desc";
        this.value = value;
        this.save = save;
    }

    static <T> String getLangKey(ForgeConfigSpec.ConfigValue<T> spec) {
        return Reference.MODID + ".config." + String.join(".", spec.getPath());
    }

    @Override
    public final void render(int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime) {
        client.fontRenderer.drawStringWithShadow(title.getFormattedText(), rowLeft + 10, rowTop + (height / 4.0f) + (client.fontRenderer.FONT_HEIGHT / 2.0f), 16777215);
        drawValue(width, height, rowLeft, rowTop, mouseX, mouseY, hovered, deltaTime);
        this.x = rowLeft;
    }

    public void save() {
        save.accept(value);
    }

    public IGuiEventListener getListener() {
        return null;
    }

    public TextComponent getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getX() {
        return x;
    }

    protected abstract void drawValue(int entryWidth, int entryHeight, int x, int y, int mouseX, int mouseY, boolean selected, float partialTicks);
}
