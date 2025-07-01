package bspkrs.armorstatushud.render;

import bspkrs.armorstatushud.ArmorStatusHUD;
import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.utils.ColorThreshold;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RenderHandler {
    private static final List<HUDElement> elements = new ArrayList<>();
    
    // Performance optimization: Cache color list and only regenerate when config changes
    private static List<ColorThreshold> cachedColorList = null;
    private static String lastColorConfig = null;
    
    // Performance optimization: Cache element collection state
    private static int lastInventoryChangeCount = -1;
    private static int cachedElementsWidth = 0;
    private static boolean elementsDirty = true;

    static List<ColorThreshold> getColorList() {
        String currentColorConfig = Config.Client.DAMAGE_COLOR_LIST.get();
        
        // Performance optimization: Only parse color list when configuration changes
        if (cachedColorList == null || !currentColorConfig.equals(lastColorConfig)) {
            cachedColorList = parseColorList(currentColorConfig);
            lastColorConfig = currentColorConfig;
        }
        
        return cachedColorList;
    }
    
    private static List<ColorThreshold> parseColorList(String colorConfig) {
        List<ColorThreshold> colorList = new ArrayList<>();

        try {
            String[] colorPairs = colorConfig.split(";");
            for (String s : colorPairs) {
                String[] ct = s.split(",");
                colorList.add(new ColorThreshold(Integer.parseInt(ct[0].trim()), ct[1].trim()));
            }
        } catch (Throwable e) {
            ArmorStatusHUD.LOGGER.warn("Error encountered parsing damageColorList: {}", colorConfig);
            ArmorStatusHUD.LOGGER.warn("Reverting to defaultColorList: {}", Config.DEFAULT_COLOR_LIST);

            String[] defaultPairs = Config.DEFAULT_COLOR_LIST.split(";");
            for (String s : defaultPairs) {
                String[] ct = s.split(",");
                colorList.add(new ColorThreshold(Integer.parseInt(ct[0].trim()), ct[1].trim()));
            }
        }

        Collections.sort(colorList);
        return colorList;
    }

    static void onTickInGame(GuiGraphics graphics, Minecraft minecraft) {
        boolean showInChat = (minecraft.screen instanceof ChatScreen) && Config.Client.SHOW_IN_CHAT.get();

        if (Config.Client.ENABLED.get() && (minecraft.screen == null || showInChat) && !minecraft.options.hideGui) {
            displayArmorStatus(graphics, minecraft);
        }
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
        if (minecraft.player == null) {
            elements.clear();
            elementsDirty = true;
            return;
        }
        
        // Performance optimization: Only rebuild elements when inventory changes
        // Using hashCode as a simple change detection mechanism
        int currentInventoryHash = minecraft.player.getInventory().hashCode();
        if (currentInventoryHash == lastInventoryChangeCount && !elementsDirty) {
            return; // No changes, use cached elements
        }
        
        elements.clear();
        elementsDirty = false;
        lastInventoryChangeCount = currentInventoryHash;

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
        
        // Recalculate cached width when elements change
        cachedElementsWidth = calculateElementsWidth();
    }

    private static int getElementsWidth() {
        // Performance optimization: Use cached width if elements haven't changed
        if (!elementsDirty && cachedElementsWidth > 0) {
            return cachedElementsWidth;
        }
        
        cachedElementsWidth = calculateElementsWidth();
        return cachedElementsWidth;
    }
    
    // Performance optimization: Replace stream with simple loop
    private static int calculateElementsWidth() {
        int totalWidth = 0;
        for (HUDElement element : elements) {
            totalWidth += element.width();
        }
        return totalWidth;
    }

    private static void displayArmorStatus(GuiGraphics graphics, Minecraft minecraft) {
        getHUDElements(minecraft);

        if (!elements.isEmpty()) {
            int yOffset = Config.Client.SHOW_ITEM_NAME.get() ? 18 : 16;

            if (Config.Client.LIST_MODE.get() == Config.ListMode.VERTICAL) {
                int yBase = getY(elements.size(), yOffset);

                for (HUDElement element : elements) {
                    element.renderToHud(graphics, Config.Client.ALIGN_MODE.get().name().toLowerCase().contains("right") ? getX(0) : getX(element.width()), yBase);
                    yBase += yOffset;
                }
            } else if (Config.Client.LIST_MODE.get() == Config.ListMode.HORIZONTAL) {
                int xBase = getX(getElementsWidth());
                int yBase = getY(1, yOffset);
                int prevX = 0;

                for (HUDElement element : elements) {
                    element.renderToHud(graphics, xBase + prevX + (Config.Client.ALIGN_MODE.get().name().toLowerCase().contains("right") ? element.width() : 0), yBase);
                    prevX += element.width();
                }
            }
        }
    }
    
    // Performance optimization: Mark elements as dirty when configuration changes
    public static void markElementsDirty() {
        elementsDirty = true;
    }
}
