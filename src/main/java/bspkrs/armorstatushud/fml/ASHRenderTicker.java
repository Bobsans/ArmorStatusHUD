package bspkrs.armorstatushud.fml;

import bspkrs.armorstatushud.ArmorStatusHUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ASHRenderTicker {
    private static boolean isRegistered = false;
    private Minecraft client;

    ASHRenderTicker() {
        client = FMLClientHandler.instance().getClient();
        isRegistered = true;
    }

    public static boolean isRegistered() {
        return isRegistered;
    }

    @SubscribeEvent
    public void onTick(RenderTickEvent event) {
        if (event.phase.equals(Phase.START))
            return;

        if (!ArmorStatusHUD.onTickInGame(client)) {
            MinecraftForge.EVENT_BUS.unregister(this);
            isRegistered = false;
        }
    }
}
