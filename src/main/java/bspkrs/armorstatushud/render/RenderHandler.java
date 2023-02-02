package bspkrs.armorstatushud.render;

import bspkrs.armorstatushud.ArmorStatusHUD;
import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.utils.ColorThreshold;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RenderHandler {
    private static final List<HUDElement> elements = new ArrayList<>();

    static List<ColorThreshold> getColorList() {
        List<ColorThreshold> colorList = new ArrayList<>();

        try {
            for (String s : Config.Client.DAMAGE_COLOR_LIST.get().split(";")) {
                String[] ct = s.split(",");
                colorList.add(new ColorThreshold(Integer.parseInt(ct[0].trim()), ct[1].trim()));
            }
        } catch (Throwable e) {
            ArmorStatusHUD.LOGGER.warn("Error encountered parsing damageColorList: " + Config.Client.DAMAGE_COLOR_LIST.get());
            ArmorStatusHUD.LOGGER.warn("Reverting to defaultColorList: " + Config.DEFAULT_COLOR_LIST);

            for (String s : Config.DEFAULT_COLOR_LIST.split(";")) {
                String[] ct = s.split(",");
                colorList.add(new ColorThreshold(Integer.parseInt(ct[0].trim()), ct[1].trim()));
            }
        }

        Collections.sort(colorList);

        return colorList;
    }

    static boolean onTickInGame(Minecraft minecraft) {
        boolean showInChat = (minecraft.screen instanceof ChatScreen) && Config.Client.SHOW_IN_CHAT.get();
        if (Config.Client.ENABLED.get() && (minecraft.screen == null || showInChat) && !minecraft.options.hideGui) {
            displayArmorStatus(minecraft);
        }

        return true;
    }

    private static int getX(int width) {
        String alignMode = Config.Client.ALIGN_MODE.get().name().toLowerCase();

        if (alignMode.contains("center")) {
            return ((Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2) - (width / 2)) + (Config.Client.APPLY_X_OFFSET_TO_CENTER.get() ? Config.Client.X_OFFSET.get() : 0);
        } else if (alignMode.contains("right")) {
            return Minecraft.getInstance().getWindow().getGuiScaledWidth() - width - Config.Client.X_OFFSET.get();
        } else {
            return Config.Client.X_OFFSET.get();
        }
    }

    private static int getY(int rowCount, int height) {
        String alignMode = Config.Client.ALIGN_MODE.get().name().toLowerCase();

        if (alignMode.contains("middle")) {
            return ((Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2) - ((rowCount * height) / 2)) + (Config.Client.APPLY_Y_OFFSET_TO_MIDDLE.get() ? Config.Client.Y_OFFSET.get() : 0);
        } else if (alignMode.equals("bottomleft") || alignMode.equals("bottomright")) {
            return Minecraft.getInstance().getWindow().getGuiScaledHeight() - (rowCount * height) - Config.Client.Y_OFFSET.get();
        } else if (alignMode.equals("bottomcenter")) {
            return Minecraft.getInstance().getWindow().getGuiScaledHeight() - (rowCount * height) - Config.Client.Y_OFFSET_BOTTOM_CENTER.get();
        } else {
            return Config.Client.Y_OFFSET.get();
        }
    }

    private static void getHUDElements(Minecraft minecraft) {
        elements.clear();

        if (minecraft.player != null) {
            for (int i = 3; i >= -2; i--) {
                ItemStack stack = null;

                if (i == -1 && Config.Client.SHOW_EQUIPPED_ITEM.get()) {
                    stack = minecraft.player.getMainHandItem();
                } else if (i == -2 && Config.Client.SHOW_OFFHAND_ITEM.get()) {
                    stack = minecraft.player.getOffhandItem();
                } else if (i != -1 && i != -2) {
                    stack = minecraft.player.getInventory().armor.get(i);
                }

                if (stack != null && !stack.isEmpty()) {
                    elements.add(new HUDElement(stack, 16, 16, 2, i > -1));
                }
            }
        }
    }

    private static int getElementsWidth() {
        return elements.stream().map(HUDElement::width).reduce(0, Integer::sum);
    }

    private static void displayArmorStatus(Minecraft minecraft) {
        getHUDElements(minecraft);

        if (elements.size() > 0) {
            int yOffset = Config.Client.SHOW_ITEM_NAME.get() ? 18 : 16;

            if (Config.Client.LIST_MODE.get() == Config.ListMode.VERTICAL) {
                int yBase = getY(elements.size(), yOffset);
                PoseStack poseStack = new PoseStack();

                for (HUDElement element : elements) {
                    element.renderToHud(poseStack, Config.Client.ALIGN_MODE.get().name().toLowerCase().contains("right") ? getX(0) : getX(element.width()), yBase);
                    yBase += yOffset;
                }
            } else if (Config.Client.LIST_MODE.get() == Config.ListMode.HORIZONTAL) {
                int xBase = getX(getElementsWidth());
                int yBase = getY(1, yOffset);
                int prevX = 0;
                PoseStack poseStack = new PoseStack();

                for (HUDElement element : elements) {
                    element.renderToHud(poseStack, xBase + prevX + (Config.Client.ALIGN_MODE.get().name().toLowerCase().contains("right") ? element.width() : 0), yBase);
                    prevX += element.width();
                }
            }
        }
    }
}
