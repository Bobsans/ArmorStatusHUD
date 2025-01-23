package bspkrs.armorstatushud.render;

import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.utils.ColorThreshold;
import bspkrs.armorstatushud.utils.HUDUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

class HUDElement {
    private final Minecraft minecraft = Minecraft.getInstance();

    private final ItemStack itemStack;
    private final int iconWidth;
    private final int iconHeight;
    private final int padWidth;
    private final boolean isArmor;

    private int elementWidth;
    private int elementHeight;
    private Component itemName = Component.literal("");
    private int itemNameWidth;
    private String itemDamage = "";
    private int itemDamageWidth;

    HUDElement(ItemStack itemStack, int iconWidth, int iconHeight, int padWidth, boolean isArmor) {
        this.itemStack = itemStack;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.padWidth = padWidth;
        this.isArmor = isArmor;

        initSize();
    }

    int width() {
        return elementWidth;
    }

    private void initSize() {
        elementHeight = Config.Client.SHOW_ITEM_NAME.get() ? Math.max(Minecraft.getInstance().font.lineHeight * 2, iconHeight) : Math.max(minecraft.font.lineHeight, iconHeight);

        if (itemStack != null) {
            int damage;
            int maxDamage;

            if (((isArmor && Config.Client.SHOW_ARMOR_DAMAGE.get()) || (!isArmor && Config.Client.SHOW_ITEM_DAMAGE.get())) && itemStack.isDamageableItem()) {
                maxDamage = itemStack.getMaxDamage();
                damage = maxDamage - itemStack.getDamageValue();

                if (Config.Client.DAMAGE_DISPLAY_TYPE.get() == Config.DamageDisplayType.VALUE) {
                    itemDamage = "\247" + ColorThreshold.getColorCode(RenderHandler.getColorList(), (Config.Client.DAMAGE_THRESHOLD_TYPE.get() == Config.DamageTresholdType.PERCENT ? (damage * 100) / maxDamage : damage)) + damage + (Config.Client.SHOW_MAX_DAMAGE.get() ? "/" + maxDamage : "");
                } else if (Config.Client.DAMAGE_DISPLAY_TYPE.get() == Config.DamageDisplayType.PERCENT) {
                    itemDamage = "\247" + ColorThreshold.getColorCode(RenderHandler.getColorList(), (Config.Client.DAMAGE_THRESHOLD_TYPE.get() == Config.DamageTresholdType.PERCENT ? (damage * 100) / maxDamage : damage)) + ((damage * 100) / maxDamage) + "%";
                }
            }

            itemDamageWidth = minecraft.font.width(HUDUtils.stripCtrl(Component.literal(itemDamage)));
            elementWidth = padWidth + iconWidth + padWidth + itemDamageWidth;

            if (Config.Client.SHOW_ITEM_NAME.get()) {
                itemName = itemStack.getDisplayName();
                elementWidth = padWidth + iconWidth + padWidth + Math.max(minecraft.font.width(HUDUtils.stripCtrl(itemName)), itemDamageWidth);
            }

            itemNameWidth = minecraft.font.width(HUDUtils.stripCtrl(itemName));
        }
    }

    void renderToHud(GuiGraphics graphics, int x, int y) {
        if (Config.Client.ALIGN_MODE.get().name().toLowerCase().contains("right")) {
            graphics.renderItem(this.itemStack, x - (iconWidth + padWidth), y, 0, 100);
            HUDUtils.renderItemOverlayIntoGUI(graphics, minecraft.font, this.itemStack, x - (iconWidth + padWidth), y, Config.Client.SHOW_DAMAGE_OVERLAY.get(), Config.Client.SHOW_ITEM_COUNT.get());

            if (Config.Client.SHOW_ITEM_NAME.get()) {
                graphics.drawString(minecraft.font, itemName, x - (iconWidth + padWidth * 2) - itemNameWidth, y, 0xffffff, true);
                graphics.drawString(minecraft.font, itemDamage, x - (iconWidth + padWidth * 2) - itemDamageWidth, y + (elementHeight / 2.0F), 0xffffff, true);
            } else {
                graphics.drawString(minecraft.font, itemDamage, x - (iconWidth + padWidth * 2) - itemDamageWidth, y + (elementHeight / 4.0F), 0xffffff, true);
            }
        } else {
            graphics.renderItem(this.itemStack, x, y, 0, 100);
            HUDUtils.renderItemOverlayIntoGUI(graphics, minecraft.font, this.itemStack, x, y, Config.Client.SHOW_DAMAGE_OVERLAY.get(), Config.Client.SHOW_ITEM_COUNT.get());

            if (Config.Client.SHOW_ITEM_NAME.get()) {
                graphics.drawString(minecraft.font, itemName, x + iconWidth + padWidth, y, 0xffffff, true);
                graphics.drawString(minecraft.font, itemDamage, x + iconWidth + padWidth, y + (elementHeight / 2.0F), 0xffffff, true);
            } else {
                graphics.drawString(minecraft.font, itemDamage, x + iconWidth + padWidth, y + (elementHeight / 4.0F), 0xffffff, true);
            }
        }

        graphics.flush();
    }
}
