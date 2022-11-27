package bspkrs.armorstatushud.proxy;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.gui.ConfigGUI;
import bspkrs.armorstatushud.render.RenderTicker;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy implements IProxy {
    public void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderTicker(Minecraft.getInstance()));

        ModList.get()
            .getModContainerById(Reference.MODID)
            .ifPresent((consumer) ->
                consumer.registerExtensionPoint(
                    ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigGUI())
                )
            );
    }
}
