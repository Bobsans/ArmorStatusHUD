package bspkrs.armorstatushud.render;

import bspkrs.armorstatushud.ArmorStatusHUD;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;

public class RenderTicker {
    public static void render(GuiGraphics graphics, DeltaTracker deltaTracker) {
        RenderHandler.onTickInGame(graphics, Minecraft.getInstance());
    }

    public static void onRegisterGuiLayer(RegisterGuiLayersEvent event) {
        event.registerBelowAll(ResourceLocation.fromNamespaceAndPath(ArmorStatusHUD.MOD_ID, "gui"), RenderTicker::render);
    }
}
