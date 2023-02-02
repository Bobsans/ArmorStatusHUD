package bspkrs.armorstatushud.binding;

import bspkrs.armorstatushud.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class KeyBinding {
    public static final KeyMapping OPEN_SETTINGS = new KeyMapping(
        Reference.MODID + ".keys.open_settings",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_MULTIPLY,
        Reference.MODID + ".keys.group"
    );

    @SubscribeEvent
    public static void onRegisterKeymappings(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.OPEN_SETTINGS);
    }
}
