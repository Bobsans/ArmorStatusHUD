package bspkrs.armorstatushud.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HUDUtils {
    public static void renderItemOverlayIntoGUI(GuiGraphics graphics, Font font, ItemStack stack, int x, int y, boolean showDamageBar, boolean showCount) {
        if (!stack.isEmpty() && (showDamageBar || showCount)) {
            if (stack.isDamaged() && showDamageBar) {
                int barWidth = stack.getBarWidth();
                int barColor = stack.getBarColor();

                RenderSystem.disableDepthTest();
                RenderSystem.disableCull();
                RenderSystem.disableBlend();

                fillRect(x + 2, y + 13, 13, 2, 0, 0, 0, 255);
                fillRect(x + 2, y + 13, barWidth, 1, barColor >> 16 & 255, barColor >> 8 & 255, barColor & 255, 255);

                RenderSystem.enableBlend();
                RenderSystem.enableCull();
                RenderSystem.enableDepthTest();
            }

            if (showCount) {
                int count = 0;

                if (Minecraft.getInstance().player != null) {
                    if (stack.getMaxStackSize() > 1) {
                        count = countInInventory(Minecraft.getInstance().player, stack.getItem(), stack.getDamageValue());
                    } else if (stack.getItem().equals(Items.BOW) || stack.getItem().equals(Items.CROSSBOW)) {
                        count = countInInventory(Minecraft.getInstance().player, Items.ARROW);
                    }
                }

                if (count > 1) {
                    String countString = String.valueOf(count);

                    graphics.pose().pushPose();
                    graphics.pose().translate(0, 0, 300);
                    graphics.drawString(font, countString, (x + 19) - 2 - font.width(countString), y + 6 + 3, 16777215);
                    graphics.pose().popPose();
                }
            }
        }
    }

    private static void fillRect(int x, int y, int width, int height, int r, int g, int b, int a) {
        var builder = RenderSystem.renderThreadTesselator().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        builder.addVertex(x, y, 0.0f).setColor(r, g, b, a);
        builder.addVertex(x, y + height, 0.0f).setColor(r, g, b, a);
        builder.addVertex(x + width, y + height, 0.0f).setColor(r, g, b, a);
        builder.addVertex(x + width, y, 0.0f).setColor(r, g, b, a);
        RenderSystem.setShader(CoreShaders.POSITION_COLOR);
        BufferUploader.drawWithShader(builder.buildOrThrow());
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
