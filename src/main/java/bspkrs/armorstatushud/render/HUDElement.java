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
    
    // Performance optimization: Cache calculated values to avoid recalculation
    private boolean sizeCached = false;
    private int lastMaxDamage = -1;
    private int lastDamageValue = -1;

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
            // Performance optimization: Only recalculate if damage values changed
            boolean needsRecalculation = !sizeCached || 
                (itemStack.isDamageableItem() && 
                 (lastMaxDamage != itemStack.getMaxDamage() || lastDamageValue != itemStack.getDamageValue()));
            
            if (needsRecalculation) {
                calculateDamageString();
                calculateItemName();
                calculateElementWidth();
                
                // Cache the calculated values
                sizeCached = true;
                if (itemStack.isDamageableItem()) {
                    lastMaxDamage = itemStack.getMaxDamage();
                    lastDamageValue = itemStack.getDamageValue();
                }
            }
        }
    }
    
    // Performance optimization: Separate damage string calculation
    private void calculateDamageString() {
        itemDamage = "";
        
        if (itemStack.isDamageableItem() && 
            ((isArmor && Config.Client.SHOW_ARMOR_DAMAGE.get()) || (!isArmor && Config.Client.SHOW_ITEM_DAMAGE.get()))) {
            
            int maxDamage = itemStack.getMaxDamage();
            int damage = maxDamage - itemStack.getDamageValue();
            String colorCode = ColorThreshold.getColorCode(RenderHandler.getColorList(), 
                (Config.Client.DAMAGE_THRESHOLD_TYPE.get() == Config.DamageTresholdType.PERCENT ? (damage * 100) / maxDamage : damage));

            // Performance optimization: Use StringBuilder for string concatenation
            StringBuilder damageBuilder = new StringBuilder();
            damageBuilder.append("\247").append(colorCode);
            
            if (Config.Client.DAMAGE_DISPLAY_TYPE.get() == Config.DamageDisplayType.VALUE) {
                damageBuilder.append(damage);
                if (Config.Client.SHOW_MAX_DAMAGE.get()) {
                    damageBuilder.append("/").append(maxDamage);
                }
            } else if (Config.Client.DAMAGE_DISPLAY_TYPE.get() == Config.DamageDisplayType.PERCENT) {
                damageBuilder.append((damage * 100) / maxDamage).append("%");
            }
            
            itemDamage = damageBuilder.toString();
        }
        
        itemDamageWidth = minecraft.font.width(HUDUtils.stripCtrl(Component.literal(itemDamage)));
    }
    
    // Performance optimization: Separate item name calculation
    private void calculateItemName() {
        if (Config.Client.SHOW_ITEM_NAME.get()) {
            itemName = itemStack.getDisplayName();
            itemNameWidth = minecraft.font.width(HUDUtils.stripCtrl(itemName));
        } else {
            itemName = Component.literal("");
            itemNameWidth = 0;
        }
    }
    
    // Performance optimization: Separate width calculation
    private void calculateElementWidth() {
        elementWidth = padWidth + iconWidth + padWidth + itemDamageWidth;

        if (Config.Client.SHOW_ITEM_NAME.get()) {
            elementWidth = padWidth + iconWidth + padWidth + Math.max(itemNameWidth, itemDamageWidth);
        }
    }

    void renderToHud(GuiGraphics graphics, int x, int y) {
        // Performance optimization: Calculate positions once
        int iconX, iconY, textX, textY1, textY2;
        boolean rightAlign = Config.Client.ALIGN_MODE.get().name().toLowerCase().contains("right");
        
        if (rightAlign) {
            iconX = x - (iconWidth + padWidth);
            iconY = y;
            textX = x - (iconWidth + padWidth * 2);
            
            if (Config.Client.SHOW_ITEM_NAME.get()) {
                textY1 = y;
                textY2 = y + (elementHeight / 2);
            } else {
                textY1 = textY2 = y + (elementHeight / 4);
            }
        } else {
            iconX = x;
            iconY = y;
            textX = x + iconWidth + padWidth;
            
            if (Config.Client.SHOW_ITEM_NAME.get()) {
                textY1 = y;
                textY2 = y + (elementHeight / 2);
            } else {
                textY1 = textY2 = y + (elementHeight / 4);
            }
        }
        
        // Render item icon
        graphics.renderItem(this.itemStack, iconX, iconY, 0, 100);
        HUDUtils.renderItemOverlayIntoGUI(graphics, minecraft.font, this.itemStack, iconX, iconY, 
            Config.Client.SHOW_DAMAGE_OVERLAY.get(), Config.Client.SHOW_ITEM_COUNT.get());

        // Render text
        if (Config.Client.SHOW_ITEM_NAME.get()) {
            if (rightAlign) {
                graphics.drawString(minecraft.font, itemName, textX - itemNameWidth, (int)textY1, 0xffffff, true);
                graphics.drawString(minecraft.font, itemDamage, textX - itemDamageWidth, (int)textY2, 0xffffff, true);
            } else {
                graphics.drawString(minecraft.font, itemName, textX, (int)textY1, 0xffffff, true);
                graphics.drawString(minecraft.font, itemDamage, textX, (int)textY2, 0xffffff, true);
            }
        } else {
            if (rightAlign) {
                graphics.drawString(minecraft.font, itemDamage, textX - itemDamageWidth, (int)textY1, 0xffffff, true);
            } else {
                graphics.drawString(minecraft.font, itemDamage, textX, (int)textY1, 0xffffff, true);
            }
        }

        graphics.flush();
    }
}
