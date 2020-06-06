package bspkrs.armorstatushud.gui.widgets.options;

import bspkrs.armorstatushud.gui.ConfigGUI;
import bspkrs.armorstatushud.gui.widgets.options.value.OptionsEntryValue;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class OptionsListWidget extends AbstractList<OptionsListWidget.Entry> {
    private final ConfigGUI owner;
    private final Runnable diskWriter;

    private OptionsListWidget(ConfigGUI owner, Minecraft minecraft, int width, int height, int x, int y, int entryHeight, Runnable diskWriter) {
        super(minecraft, width, height, x, y, entryHeight);

        this.owner = owner;
        this.diskWriter = diskWriter;
        this.renderSelection = false;
    }

    public OptionsListWidget(ConfigGUI owner, Minecraft client, int width, int height, int x, int y, int entryHeight) {
        this(owner, client, width, height, x, y, entryHeight, null);
    }

    @Override
    public int getRowWidth() {
        return Math.min(minecraft.mainWindow.getScaledWidth(), 500);
    }

    @Override
    protected int getScrollbarPosition() {
        return this.width - 12;
    }

    public void render(int x, int y, float partialTicks) {
        this.renderBackground();
        int scrollbarX = this.getScrollbarPosition();
        int scrollbarVolumeX = scrollbarX + 6;

        GlStateManager.disableLighting();
        GlStateManager.disableFog();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        this.minecraft.getTextureManager().bindTexture(BACKGROUND_LOCATION);

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        int itemsX = this.getRowLeft();
        int itemsY = this.y0 + 4 - (int)this.getScrollAmount();

        this.renderList(itemsX, itemsY, x, y, partialTicks);

        GlStateManager.disableDepthTest();

        this.renderHoleBackground(0, this.y0, 255, 255);
        this.renderHoleBackground(this.y1, this.height, 255, 255);

        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
        GlStateManager.disableAlphaTest();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.disableTexture();

        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(this.x0, (this.y0 + 4), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        bufferBuilder.pos(this.x1, (this.y0 + 4), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
        bufferBuilder.pos(this.x1, this.y0, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferBuilder.pos(this.x0, this.y0, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
        bufferBuilder.pos(this.x0, this.y1, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        bufferBuilder.pos(this.x1, this.y1, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
        bufferBuilder.pos(this.x1, (this.y1 - 4), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        bufferBuilder.pos(this.x0, (this.y1 - 4), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
        tessellator.draw();

        int int_8 = Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4));

        if (int_8 > 0) {
            int int_9 = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            int_9 = MathHelper.clamp(int_9, 32, this.y1 - this.y0 - 8);
            int int_10 = (int)this.getScrollAmount() * (this.y1 - this.y0 - int_9) / int_8 + this.y0;
            if (int_10 < this.y0) {
                int_10 = this.y0;
            }

            bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.pos(scrollbarX, this.y1, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(scrollbarVolumeX, this.y1, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(scrollbarVolumeX, this.y0, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(scrollbarX, this.y0, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.pos(scrollbarX, (int_10 + int_9), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            bufferBuilder.pos(scrollbarVolumeX, (int_10 + int_9), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
            bufferBuilder.pos(scrollbarVolumeX, int_10, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferBuilder.pos(scrollbarX, int_10, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferBuilder.pos(scrollbarX, (int_10 + int_9 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            bufferBuilder.pos((scrollbarVolumeX - 1), (int_10 + int_9 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
            bufferBuilder.pos((scrollbarVolumeX - 1), int_10, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            bufferBuilder.pos(scrollbarX, int_10, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
            tessellator.draw();
        }

        this.renderDecorations(x, y);

        GlStateManager.enableTexture();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlphaTest();
        GlStateManager.disableBlend();
    }

    @Override
    public void mouseMoved(double x, double y) {
        for (OptionsListWidget.Entry child : this.children()) {
            if (child.isMouseOver(x, y)) {
                setSelected(child);
            }
        }
    }

    public void save() {
        children().stream().filter((e) -> e instanceof OptionsEntryValue).map((e) -> (OptionsEntryValue)e).forEach(OptionsEntryValue::save);

        if (diskWriter != null) {
            diskWriter.run();
        }
    }

    public void add(Entry entry) {
        if (entry instanceof OptionsEntryValue) {
            IGuiEventListener element = ((OptionsEntryValue)entry).getListener();
            if (element != null) {
                owner.addListener(element);
            }
        }
        addEntry(entry);
    }

    public abstract static class Entry extends AbstractList.AbstractListEntry<Entry> {
        protected final Minecraft client;

        protected Entry() {
            this.client = Minecraft.getInstance();
        }

        @Override
        public abstract void render(int index, int rowTop, int rowLeft, int width, int height, int mouseX, int mouseY, boolean hovered, float deltaTime);
    }
}
