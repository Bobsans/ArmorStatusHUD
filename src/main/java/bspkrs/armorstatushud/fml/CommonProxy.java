package bspkrs.armorstatushud.fml;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

public class CommonProxy {
    private Logger logger;

    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
    }

    public void init(FMLInitializationEvent event) {
        logger.error("***********************************************************************************");
        logger.error("* ArmorStatusHUD is a CLIENT-ONLY mod. Installing it on your server is pointless. *");
        logger.error("***********************************************************************************");
    }
}
