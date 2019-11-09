package bspkrs.armorstatushud;

import bspkrs.client.util.ColorThreshold;
import bspkrs.client.util.HUDUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

class HUDElement {
    private final ItemStack itemStack;
    private final int iconW;
    private final int iconH;
    private final int padW;
    private final boolean isArmor;
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private int elementW;
    private int elementH;
    private String itemName = "";
    private int itemNameW;
    private String itemDamage = "";
    private int itemDamageW;

    HUDElement(ItemStack itemStack, int iconW, int iconH, int padW, boolean isArmor) {
        this.itemStack = itemStack;
        this.iconW = iconW;
        this.iconH = iconH;
        this.padW = padW;
        this.isArmor = isArmor;

        initSize();
    }

    int width() {
        return elementW;
    }

    int height() {
        return elementH;
    }

    private void initSize() {
        elementH = ArmorStatusHUD.enableItemName ? Math.max(Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT * 2, iconH) : Math.max(minecraft.fontRenderer.FONT_HEIGHT, iconH);

        if (itemStack != null) {
            int damage;
            int maxDamage;

            if (((isArmor && ArmorStatusHUD.showArmorDamage) || (!isArmor && ArmorStatusHUD.showItemDamage)) && itemStack.isItemStackDamageable()) {
                maxDamage = itemStack.getMaxDamage() + 1;
                damage = maxDamage - itemStack.getItemDamage();

                if (ArmorStatusHUD.damageDisplayType.equalsIgnoreCase("value")) {
                    itemDamage = "\247" + ColorThreshold.getColorCode(ArmorStatusHUD.colorList, (ArmorStatusHUD.damageThresholdType.equalsIgnoreCase("percent") ? (damage * 100) / maxDamage : damage)) + damage + (ArmorStatusHUD.showMaxDamage ? "/" + maxDamage : "");
                } else if (ArmorStatusHUD.damageDisplayType.equalsIgnoreCase("percent")) {
                    itemDamage = "\247" + ColorThreshold.getColorCode(ArmorStatusHUD.colorList, (ArmorStatusHUD.damageThresholdType.equalsIgnoreCase("percent") ? (damage * 100) / maxDamage : damage)) + ((damage * 100) / maxDamage) + "%";
                }
            }

            itemDamageW = minecraft.fontRenderer.getStringWidth(HUDUtils.stripCtrl(itemDamage));
            elementW = padW + iconW + padW + itemDamageW;

            if (ArmorStatusHUD.enableItemName) {
                itemName = itemStack.getDisplayName();
                elementW = padW + iconW + padW + Math.max(minecraft.fontRenderer.getStringWidth(HUDUtils.stripCtrl(itemName)), itemDamageW);
            }

            itemNameW = minecraft.fontRenderer.getStringWidth(HUDUtils.stripCtrl(itemName));
        }
    }

    void renderToHud(int x, int y) {
        RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();
        itemRenderer.zLevel = 100.0F;

        if (ArmorStatusHUD.alignMode.toLowerCase().contains("right")) {
            itemRenderer.renderItemAndEffectIntoGUI(itemStack, x - (iconW + padW), y);
            HUDUtils.renderItemOverlayIntoGUI(minecraft.fontRenderer, itemStack, x - (iconW + padW), y, ArmorStatusHUD.showDamageOverlay, ArmorStatusHUD.showItemCount);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();

            minecraft.fontRenderer.drawStringWithShadow(itemName + "\247r", x - (padW + iconW + padW) - itemNameW, y, 0xffffff);
            minecraft.fontRenderer.drawStringWithShadow(itemDamage + "\247r", x - (padW + iconW + padW) - itemDamageW, y + (ArmorStatusHUD.enableItemName ? elementH / 2 : elementH / 4), 0xffffff);
        } else {
            itemRenderer.renderItemAndEffectIntoGUI(itemStack, x, y);
            HUDUtils.renderItemOverlayIntoGUI(minecraft.fontRenderer, itemStack, x, y, ArmorStatusHUD.showDamageOverlay, ArmorStatusHUD.showItemCount);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();

            minecraft.fontRenderer.drawStringWithShadow(itemName + "\247r", x + iconW + padW, y, 0xffffff);
            minecraft.fontRenderer.drawStringWithShadow(itemDamage + "\247r", x + iconW + padW, y + (ArmorStatusHUD.enableItemName ? elementH / 2 : elementH / 4), 0xffffff);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
