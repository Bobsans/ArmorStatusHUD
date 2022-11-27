package bspkrs.armorstatushud;

import bspkrs.armorstatushud.commands.CommandArmorStatus;
import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.network.NetworkingManager;
import bspkrs.armorstatushud.proxy.ClientProxy;
import bspkrs.armorstatushud.proxy.IProxy;
import bspkrs.armorstatushud.proxy.ServerProxy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MODID)
public class ArmorStatusHUD {
    public static final Logger LOGGER = LogManager.getLogger();

    private static final IProxy proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public ArmorStatusHUD() {
        Config.register(ModLoadingContext.get());

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        NetworkingManager.init();
        proxy.setup(event);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandArmorStatus.register(event.getDispatcher());
    }
}
