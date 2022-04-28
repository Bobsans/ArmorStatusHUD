package bspkrs.armorstatushud.proxy;

import bspkrs.armorstatushud.render.RenderTicker;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy {
    public void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderTicker(Minecraft.getInstance()));

//        ModList.get().getModContainerById(Reference.MODID).ifPresent((consumer) ->
//            consumer.registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> (mc, parent) -> new ConfigGUI(parent))
//        );
    }
}
