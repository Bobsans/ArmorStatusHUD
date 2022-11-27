package bspkrs.armorstatushud.binding;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.gui.ConfigGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraftforge.api.distmarker.Dist.CLIENT;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = CLIENT)
public class Keys {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().player != null) {
            if (KeyBindings.OPEN_SETTINGS.isDown()) {
                Minecraft.getInstance().setScreen(new ConfigGUI());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterKeymappings(RegisterKeyMappingsEvent event) {
        event.register(KeyBindings.OPEN_SETTINGS);
    }
}
