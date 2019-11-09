package bspkrs.armorstatushud.fml;

import bspkrs.util.Const;
import bspkrs.util.ModVersionChecker;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = "@MOD_VERSION@", dependencies = "required-after:bspkrsCore@[@BSCORE_VERSION@,)", useMetadata = true, guiFactory = Reference.GUI_FACTORY)
public class ArmorStatusHUDMod {
    public static Logger logger;

    @Metadata(value = Reference.MODID)
    public static ModMetadata metadata;

    @Instance(value = Reference.MODID)
    public static ArmorStatusHUDMod instance;

    @SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_COMMON)
    public static CommonProxy proxy;

    protected String versionURL = Const.VERSION_URL + "/Minecraft/" + Const.MCVERSION + "/armorStatusHUD.version";
    protected String mcfTopic = "http://www.minecraftforum.net/topic/1114612-";
    ModVersionChecker versionChecker;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        metadata = event.getModMetadata();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }
}
