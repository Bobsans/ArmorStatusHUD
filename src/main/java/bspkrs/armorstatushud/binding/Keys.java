package bspkrs.armorstatushud.binding;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.gui.ConfigGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraftforge.api.distmarker.Dist.CLIENT;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = CLIENT)
public class Keys {
    static {
        ClientRegistry.registerKeyBinding(KeyBindings.OPEN_SETTINGS);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.OPEN_SETTINGS.isDown()) {
            Minecraft.getInstance().setScreen(new ConfigGUI());
        }
    }
}
