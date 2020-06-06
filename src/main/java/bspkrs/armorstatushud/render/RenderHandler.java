package bspkrs.armorstatushud.render;

import bspkrs.armorstatushud.ArmorStatusHUD;
import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.utils.ColorThreshold;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class RenderHandler {
    private static final List<HUDElement> elements = new ArrayList<>();

    static List<ColorThreshold> getColorList() {
        List<ColorThreshold> colorList = new ArrayList<>();

        try {
            for (String s : Config.DAMAGE_COLOR_LIST.get().split(";")) {
                String[] ct = s.split(",");
                colorList.add(new ColorThreshold(Integer.parseInt(ct[0].trim()), ct[1].trim()));
            }
        } catch (Throwable e) {
            ArmorStatusHUD.LOGGER.warn("Error encountered parsing damageColorList: " + Config.DAMAGE_COLOR_LIST.get());
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
        if (Config.ENABLED.get() && (minecraft.currentScreen == null || ((minecraft.currentScreen instanceof ChatScreen) && Config.SHOW_IN_CHAT.get())) && !minecraft.gameSettings.showDebugInfo) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            displayArmorStatus(minecraft);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }

        return true;
    }

    private static int getX(int width) {
        if (Config.ALIGN_MODE.get().name().toLowerCase().contains("center")) {
            return ((Minecraft.getInstance().mainWindow.getScaledWidth() / 2) - (width / 2)) + (Config.APPLY_X_OFFSET_TO_CENTER.get() ? Config.X_OFFSET.get() : 0);
        } else if (Config.ALIGN_MODE.get().name().toLowerCase().contains("right")) {
            return Minecraft.getInstance().mainWindow.getScaledWidth() - width - Config.X_OFFSET.get();
        } else {
            return Config.X_OFFSET.get();
        }
    }

    private static int getY(int rowCount, int height) {
        if (Config.ALIGN_MODE.get().name().toLowerCase().contains("middle")) {
            return ((Minecraft.getInstance().mainWindow.getScaledHeight() / 2) - ((rowCount * height) / 2)) + (Config.APPLY_Y_OFFSET_TO_MIDDLE.get() ? Config.Y_OFFSET.get() : 0);
        } else if (Config.ALIGN_MODE.get().name().equalsIgnoreCase("bottomleft") || Config.ALIGN_MODE.get().name().equalsIgnoreCase("bottomright")) {
            return Minecraft.getInstance().mainWindow.getScaledHeight() - (rowCount * height) - Config.Y_OFFSET.get();
        } else if (Config.ALIGN_MODE.get().name().equalsIgnoreCase("bottomcenter")) {
            return Minecraft.getInstance().mainWindow.getScaledHeight() - (rowCount * height) - Config.Y_OFFSET_BOTTOM_CENTER.get();
        } else {
            return Config.Y_OFFSET.get();
        }
    }

    private static void getHUDElements(Minecraft minecraft) {
        elements.clear();

        for (int i = 3; i >= -2; i--) {
            ItemStack itemStack = null;

            if (i == -1 && Config.SHOW_EQUIPPED_ITEM.get()) {
                itemStack = minecraft.player.getHeldItemMainhand();
            } else if (i == -2 && Config.SHOW_OFFHAND_ITEM.get()) {
                itemStack = minecraft.player.getHeldItemOffhand();
            } else if (i != -1 && i != -2) {
                itemStack = minecraft.player.inventory.armorInventory.get(i);
            }

            if (itemStack != null) {
                elements.add(new HUDElement(itemStack, 16, 16, 2, i > -1));
            }
        }
    }

    private static int getElementsWidth() {
        return elements.stream().map(HUDElement::width).reduce(0, Integer::sum);
    }

    private static void displayArmorStatus(Minecraft minecraft) {
        getHUDElements(minecraft);

        if (elements.size() > 0) {
            int yOffset = Config.ENABLE_ITEM_NAME.get() ? 18 : 16;

            if (Config.LIST_MODE.get() == Config.ListMode.VERTICAL) {
                int yBase = getY(elements.size(), yOffset);

                for (HUDElement element : elements) {
                    element.renderToHud(Config.ALIGN_MODE.get().name().toLowerCase().contains("right") ? getX(0) : getX(element.width()), yBase);
                    yBase += yOffset;
                }
            } else if (Config.LIST_MODE.get() == Config.ListMode.HORIZONTAL) {
                int xBase = getX(getElementsWidth());
                int yBase = getY(1, yOffset);
                int prevX = 0;

                for (HUDElement element : elements) {
                    element.renderToHud(xBase + prevX + (Config.ALIGN_MODE.get().name().toLowerCase().contains("right") ? element.width() : 0), yBase);
                    prevX += element.width();
                }
            }
        }
    }
}
