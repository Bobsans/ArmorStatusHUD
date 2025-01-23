package bspkrs.armorstatushud;

import bspkrs.armorstatushud.config.Config;
import bspkrs.armorstatushud.render.RenderTicker;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ArmorStatusHUD.MOD_ID)
public class ArmorStatusHUD {
    public static final String MOD_ID = "armorstatushud";
    public static final Logger LOGGER = LogManager.getLogger();

    public ArmorStatusHUD(IEventBus bus, ModContainer container) {
        Config.register(container);

        bus.addListener(RenderTicker::onRegisterGuiLayer);
    }
}
