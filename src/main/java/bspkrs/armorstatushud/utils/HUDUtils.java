package bspkrs.armorstatushud.utils;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.lwjgl.opengl.GL11;

public class HUDUtils {
    public static void renderItemOverlayIntoGUI(FontRenderer fontRenderer, ItemStack stack, int x, int y, double zLevel, boolean showDamageBar, boolean showCount) {
        if (!stack.isEmpty() && (showDamageBar || showCount)) {
            if (stack.isDamaged() && showDamageBar) {
                int dmgWidth = (int) Math.round(13.0D - ((stack.getDamage() * 13.0D) / stack.getMaxDamage()));
                int dmgColor = (int) Math.round(255.0D - ((stack.getDamage() * 255.0D) / stack.getMaxDamage()));
                int dmgColorHex = 255 - dmgColor << 16 | dmgColor << 8;
                int dmgShadowHex = (255 - dmgColor) / 4 << 16 | 16128;

                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableAlphaTest();
                RenderSystem.disableBlend();

                Tessellator tessellator = Tessellator.getInstance();
                renderQuad(tessellator, x + 2, y + 13, 13, 2, 0);
                renderQuad(tessellator, x + 2, y + 13, 12, 1, dmgShadowHex);
                renderQuad(tessellator, x + 2, y + 13, dmgWidth, 1, dmgColorHex);

                RenderSystem.enableBlend();
                RenderSystem.enableAlphaTest();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

            if (showCount) {
                int count = 0;

                if (Minecraft.getInstance().player != null) {
                    if (stack.getMaxStackSize() > 1) {
                        count = HUDUtils.countInInventory(Minecraft.getInstance().player, stack.getItem(), stack.getDamage());
                    } else if (stack.getItem().equals(Items.BOW)) {
                        count = HUDUtils.countInInventory(Minecraft.getInstance().player, Items.ARROW);
                    }
                }

                if (count > 1) {
                    String countString = String.valueOf(count);

                    MatrixStack matrixstack = new MatrixStack();
                    matrixstack.translate(0.0D, 0.0D, zLevel + 200.0D);
                    IRenderTypeBuffer.Impl buffer = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
                    fontRenderer.renderString(countString, (x + 19) - 2 - fontRenderer.getStringWidth(countString), y + 6 + 3, 16777215, true, matrixstack.getLast().getMatrix(), buffer, false, 0, 15728880);
                    buffer.finish();
                }
            }
        }
    }

    private static void renderQuad(Tessellator tessellator, int x, int y, int width, int height, int color) {
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        int a = 255;

        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(x, y, 0.0D).color(r, g, b, a).endVertex();
        buffer.pos(x, y + height, 0.0D).color(r, g, b, a).endVertex();
        buffer.pos(x + width, y + height, 0.0D).color(r, g, b, a).endVertex();
        buffer.pos(x + width, y, 0.0D).color(r, g, b, a).endVertex();
        tessellator.draw();
    }

    private static int countInInventory(PlayerEntity player, Item item) {
        return countInInventory(player, item, -1);
    }

    private static int countInInventory(PlayerEntity player, Item item, int md) {
        int count = 0;

        for (ItemStack stack : player.inventory.mainInventory) {
            if (!stack.isEmpty() && item.equals(stack.getItem()) && (md == -1 || stack.getDamage() == md)) {
                count += stack.getCount();
            }
        }

        return count;
    }

    public static String stripCtrl(String s) {
        return s.replaceAll("(?i)\247[0-9a-fklmnor]", "");
    }
}
