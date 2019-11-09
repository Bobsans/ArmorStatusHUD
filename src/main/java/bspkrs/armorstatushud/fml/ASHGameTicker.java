package bspkrs.armorstatushud.fml;

import bspkrs.bspkrscore.fml.bspkrsCoreMod;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ASHGameTicker {
    private static boolean isRegistered = false;
    private Minecraft minecraft;

    public ASHGameTicker() {
        minecraft = FMLClientHandler.instance().getClient();
        isRegistered = true;
    }

    public static boolean isRegistered() {
        return isRegistered;
    }

    @SubscribeEvent
    public void onTick(ClientTickEvent event) {
        if (event.phase.equals(Phase.START)) { return; }

        boolean keepTicking = !(minecraft != null && minecraft.player != null && minecraft.world != null);

        if (!keepTicking && isRegistered) {
            if (bspkrsCoreMod.instance.allowUpdateCheck && ArmorStatusHUDMod.instance.versionChecker != null) {
                if (!ArmorStatusHUDMod.instance.versionChecker.isCurrentVersion()) {
                    for (String msg : ArmorStatusHUDMod.instance.versionChecker.getInGameMessage()) {
                        minecraft.player.sendChatMessage(msg);
                    }
                }
            }

            MinecraftForge.EVENT_BUS.unregister(this);
            isRegistered = false;
        }
    }
}
