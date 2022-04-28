package bspkrs.armorstatushud.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HUDUtils {
    public static void renderItemOverlayIntoGUI(Font font, ItemStack stack, int x, int y, double zLevel, boolean showDamageBar, boolean showCount) {
        if (!stack.isEmpty() && (showDamageBar || showCount)) {
            if (stack.isDamaged() && showDamageBar) {
                int dmgWidth = (int) Math.round(13.0D - ((stack.getDamageValue() * 13.0D) / stack.getMaxDamage()));
                int dmgColor = (int) Math.round(255.0D - ((stack.getDamageValue() * 255.0D) / stack.getMaxDamage()));
                int dmgColorHex = 255 - dmgColor << 16 | dmgColor << 8;
                int dmgShadowHex = (255 - dmgColor) / 4 << 16 | 16128;

                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableBlend();

                Tesselator tesselator = Tesselator.getInstance();
                renderQuad(tesselator, x + 2, y + 13, 13, 2, 0);
                renderQuad(tesselator, x + 2, y + 13, 12, 1, dmgShadowHex);
                renderQuad(tesselator, x + 2, y + 13, dmgWidth, 1, dmgColorHex);

                RenderSystem.enableBlend();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

            if (showCount) {
                int count = 0;

                if (Minecraft.getInstance().player != null) {
                    if (stack.getMaxStackSize() > 1) {
                        count = HUDUtils.countInInventory(Minecraft.getInstance().player, stack.getItem(), stack.getDamageValue());
                    } else if (stack.getItem().equals(Items.BOW)) {
                        count = HUDUtils.countInInventory(Minecraft.getInstance().player, Items.ARROW);
                    }
                }

                if (count > 1) {
                    String countString = String.valueOf(count);

                    PoseStack poseStack = new PoseStack();
                    poseStack.translate(0.0D, 0.0D, zLevel + 200.0D);
                    font.draw(poseStack, countString, (x + 19) - 2 - font.width(countString), y + 6 + 3, 16777215);
                    poseStack.popPose();
                }
            }
        }
    }

    private static void renderQuad(Tesselator tessellator, int x, int y, int width, int height, int color) {
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color & 255;
        int a = 255;

        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        buffer.vertex(x, y, 0.0D).color(r, g, b, a).endVertex();
        buffer.vertex(x, y + height, 0.0D).color(r, g, b, a).endVertex();
        buffer.vertex(x + width, y + height, 0.0D).color(r, g, b, a).endVertex();
        buffer.vertex(x + width, y, 0.0D).color(r, g, b, a).endVertex();
        tessellator.end();
    }

    private static int countInInventory(Player player, Item item) {
        return countInInventory(player, item, -1);
    }

    private static int countInInventory(Player player, Item item, int md) {
        int count = 0;

        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && item.equals(stack.getItem()) && (md == -1 || stack.getDamageValue() == md)) {
                count += stack.getCount();
            }
        }

        return count;
    }

    public static <T extends Component> TextComponent stripCtrl(T text) {
        return new TextComponent(text.getString().replaceAll("(?i)\247[0-9a-fklmnor]", ""));
    }
}
