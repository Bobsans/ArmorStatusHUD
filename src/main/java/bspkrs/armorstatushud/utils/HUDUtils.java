package bspkrs.armorstatushud.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HUDUtils {
    public static void renderItemOverlayIntoGUI(Font font, ItemStack stack, int x, int y, double zLevel, boolean showDamageBar, boolean showCount) {
        if (!stack.isEmpty() && (showDamageBar || showCount)) {
            if (stack.isDamaged() && showDamageBar) {
                int barWidth = stack.getBarWidth();
                int barColor = stack.getBarColor();

                RenderSystem.disableDepthTest();
                RenderSystem.disableTexture();
                RenderSystem.disableBlend();

                Tesselator tesselator = Tesselator.getInstance();
                BufferBuilder bufferBuilder = tesselator.getBuilder();

                fillRect(bufferBuilder, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
                fillRect(bufferBuilder, x + 2, y + 13, barWidth, 1, barColor >> 16 & 255, barColor >> 8 & 255, barColor & 255, 255);

                RenderSystem.enableBlend();
                RenderSystem.enableTexture();
                RenderSystem.enableDepthTest();
            }

            if (showCount) {
                int count = 0;

                if (Minecraft.getInstance().player != null) {
                    if (stack.getMaxStackSize() > 1) {
                        count = countInInventory(Minecraft.getInstance().player, stack.getItem(), stack.getDamageValue());
                    } else if (stack.getItem().equals(Items.BOW)) {
                        count = countInInventory(Minecraft.getInstance().player, Items.ARROW);
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

    private static void fillRect(BufferBuilder builder, int x, int y, int width, int height, int r, int g, int b, int a) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        builder.vertex(x, y, 0.0D).color(r, g, b, a).endVertex();
        builder.vertex(x, y + height, 0.0D).color(r, g, b, a).endVertex();
        builder.vertex(x + width, y + height, 0.0D).color(r, g, b, a).endVertex();
        builder.vertex(x + width, y, 0.0D).color(r, g, b, a).endVertex();
        BufferUploader.drawWithShader(builder.end());
    }

    private static int countInInventory(Player player, Item item) {
        return countInInventory(player, item, -1);
    }

    private static int countInInventory(Player player, Item item, int datage) {
        int count = 0;

        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty() && item.equals(stack.getItem()) && (datage == -1 || stack.getDamageValue() == datage)) {
                count += stack.getCount();
            }
        }

        return count;
    }

    public static <T extends Component> Component stripCtrl(T text) {
        return Component.literal(text.getString().replaceAll("(?i)\247[0-9a-fklmnor]", ""));
    }
}
