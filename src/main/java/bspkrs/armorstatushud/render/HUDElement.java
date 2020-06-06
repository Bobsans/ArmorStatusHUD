package bspkrs.armorstatushud.render;

import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.utils.ColorThreshold;
import bspkrs.armorstatushud.utils.HUDUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;

class HUDElement {
    private final Minecraft minecraft = Minecraft.getInstance();

    private final ItemStack itemStack;
    private final int iconWidth;
    private final int iconHeight;
    private final int padWidth;
    private final boolean isArmor;

    private int elementWidth;
    private int elementHeight;
    private String itemName = "";
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

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    int width() {
        return elementWidth;
    }

    int height() {
        return elementHeight;
    }

    private void initSize() {
        elementHeight = Config.ENABLE_ITEM_NAME.get() ? Math.max(Minecraft.getInstance().fontRenderer.FONT_HEIGHT * 2, iconHeight) : Math.max(minecraft.fontRenderer.FONT_HEIGHT, iconHeight);

        if (itemStack != null) {
            int damage;
            int maxDamage;

            if (((isArmor && Config.SHOW_ARMOR_DAMAGE.get()) || (!isArmor && Config.SHOW_ITEM_DAMAGE.get())) && itemStack.isDamageable()) {
                maxDamage = itemStack.getMaxDamage() + 1;
                damage = maxDamage - itemStack.getDamage();

                if (Config.DAMAGE_DISPLAY_TYPE.get() == Config.DamageDisplayType.VALUE) {
                    itemDamage = "\247" + ColorThreshold.getColorCode(RenderHandler.getColorList(), (Config.DAMAGE_THRESHOLD_TYPE.get() == Config.DamageTresholdType.PERCENT ? (damage * 100) / maxDamage : damage)) + damage + (Config.SHOW_MAX_DAMAGE.get() ? "/" + maxDamage : "");
                } else if (Config.DAMAGE_DISPLAY_TYPE.get() == Config.DamageDisplayType.PERCENT) {
                    itemDamage = "\247" + ColorThreshold.getColorCode(RenderHandler.getColorList(), (Config.DAMAGE_THRESHOLD_TYPE.get() == Config.DamageTresholdType.PERCENT ? (damage * 100) / maxDamage : damage)) + ((damage * 100) / maxDamage) + "%";
                }
            }

            itemDamageWidth = minecraft.fontRenderer.getStringWidth(HUDUtils.stripCtrl(itemDamage));
            elementWidth = padWidth + iconWidth + padWidth + itemDamageWidth;

            if (Config.ENABLE_ITEM_NAME.get()) {
                itemName = itemStack.getDisplayName().getFormattedText();
                elementWidth = padWidth + iconWidth + padWidth + Math.max(minecraft.fontRenderer.getStringWidth(HUDUtils.stripCtrl(itemName)), itemDamageWidth);
            }

            itemNameWidth = minecraft.fontRenderer.getStringWidth(HUDUtils.stripCtrl(itemName));
        }
    }

    void renderToHud(int x, int y) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.zLevel = 100.0F;

        if (Config.ALIGN_MODE.get().name().toLowerCase().contains("right")) {
            itemRenderer.renderItemAndEffectIntoGUI(itemStack, x - (iconWidth + padWidth), y);
            HUDUtils.renderItemOverlayIntoGUI(minecraft.fontRenderer, itemStack, x - (iconWidth + padWidth), y, itemRenderer.zLevel, Config.SHOW_DAMAGE_OVERLAY.get(), Config.SHOW_ITEM_COUNT.get());

            if (Config.ENABLE_ITEM_NAME.get()) {
                minecraft.fontRenderer.drawStringWithShadow(itemName + "\247r", x - (iconWidth + padWidth * 2) - itemNameWidth, y, 0xffffff);
                minecraft.fontRenderer.drawStringWithShadow(itemDamage + "\247r", x - (iconWidth + padWidth * 2) - itemDamageWidth, y + (elementHeight / 2.0F), 0xffffff);
            } else {
                minecraft.fontRenderer.drawStringWithShadow(itemDamage + "\247r", x - (iconWidth + padWidth * 2) - itemDamageWidth, y + (elementHeight / 4.0F), 0xffffff);
            }
        } else {
            itemRenderer.renderItemAndEffectIntoGUI(itemStack, x, y);
            HUDUtils.renderItemOverlayIntoGUI(minecraft.fontRenderer, itemStack, x, y, itemRenderer.zLevel, Config.SHOW_DAMAGE_OVERLAY.get(), Config.SHOW_ITEM_COUNT.get());

            if (Config.ENABLE_ITEM_NAME.get()) {
                minecraft.fontRenderer.drawStringWithShadow(itemName + "\247r", x + iconWidth + padWidth, y, 0xffffff);
                minecraft.fontRenderer.drawStringWithShadow(itemDamage + "\247r", x + iconWidth + padWidth, y + (elementHeight / 2.0F), 0xffffff);
            } else {
                minecraft.fontRenderer.drawStringWithShadow(itemDamage + "\247r", x + iconWidth + padWidth, y + (elementHeight / 4.0F), 0xffffff);
            }
        }
    }
}
