package bspkrs.armorstatushud.binding;

import bspkrs.armorstatushud.gui.ConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().player != null) {
            if (KeyBinding.OPEN_SETTINGS.isDown()) {
                ConfigScreen.open();
            }
        }
    }
}
