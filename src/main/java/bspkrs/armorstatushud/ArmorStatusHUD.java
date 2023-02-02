package bspkrs.armorstatushud;

import bspkrs.armorstatushud.binding.KeyHandler;
import bspkrs.armorstatushud.commands.CommandArmorStatus;
import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.gui.ConfigScreen;
import bspkrs.armorstatushud.network.NetworkingManager;
import bspkrs.armorstatushud.render.RenderTicker;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MODID)
public class ArmorStatusHUD {
    public static final Logger LOGGER = LogManager.getLogger();

    public ArmorStatusHUD() {
        Config.register(ModLoadingContext.get());

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(KeyHandler.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NetworkingManager.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new RenderTicker(Minecraft.getInstance()));
        ConfigScreen.register(Reference.MODID, (minecraft, screen) -> new ConfigScreen());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandArmorStatus.register(event.getDispatcher());
    }
}
