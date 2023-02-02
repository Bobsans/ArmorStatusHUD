package bspkrs.armorstatushud.render;

import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.utils.ColorThreshold;
import bspkrs.armorstatushud.utils.HUDUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
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

    int height() {
        return elementHeight;
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

    void renderToHud(PoseStack stack, int x, int y) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        itemRenderer.blitOffset = 100.0F;

        if (Config.Client.ALIGN_MODE.get().name().toLowerCase().contains("right")) {
            itemRenderer.renderAndDecorateItem(this.itemStack, x - (iconWidth + padWidth), y);
            HUDUtils.renderItemOverlayIntoGUI(minecraft.font, this.itemStack, x - (iconWidth + padWidth), y, itemRenderer.blitOffset, Config.Client.SHOW_DAMAGE_OVERLAY.get(), Config.Client.SHOW_ITEM_COUNT.get());

            if (Config.Client.SHOW_ITEM_NAME.get()) {
                minecraft.font.drawShadow(stack, itemName, x - (iconWidth + padWidth * 2) - itemNameWidth, y, 0xffffff);
                minecraft.font.drawShadow(stack, itemDamage, x - (iconWidth + padWidth * 2) - itemDamageWidth, y + (elementHeight / 2.0F), 0xffffff);
            } else {
                minecraft.font.drawShadow(stack, itemDamage, x - (iconWidth + padWidth * 2) - itemDamageWidth, y + (elementHeight / 4.0F), 0xffffff);
            }
        } else {
            itemRenderer.renderAndDecorateItem(this.itemStack, x, y);
            HUDUtils.renderItemOverlayIntoGUI(minecraft.font, this.itemStack, x, y, itemRenderer.blitOffset, Config.Client.SHOW_DAMAGE_OVERLAY.get(), Config.Client.SHOW_ITEM_COUNT.get());

            if (Config.Client.SHOW_ITEM_NAME.get()) {
                minecraft.font.drawShadow(stack, itemName, x + iconWidth + padWidth, y, 0xffffff);
                minecraft.font.drawShadow(stack, itemDamage, x + iconWidth + padWidth, y + (elementHeight / 2.0F), 0xffffff);
            } else {
                minecraft.font.drawShadow(stack, itemDamage, x + iconWidth + padWidth, y + (elementHeight / 4.0F), 0xffffff);
            }
        }
    }
}
