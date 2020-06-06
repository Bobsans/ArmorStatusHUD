package bspkrs.armorstatushud.render;

import bspkrs.armorstatushud.Reference;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Dist.CLIENT)
public class RenderTicker {
    private final Minecraft minecraft;

    public RenderTicker(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!RenderHandler.onTickInGame(minecraft)) {
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }
}
