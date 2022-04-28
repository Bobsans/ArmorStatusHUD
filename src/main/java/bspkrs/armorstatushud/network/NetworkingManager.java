package bspkrs.armorstatushud.network;

import bspkrs.armorstatushud.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;


public class NetworkingManager {
    public static SimpleChannel CHANNEL;
    private static int id = 0;

    private static int nextId() {
        return id++;
    }

    public static void init() {
        CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Reference.MODID, "main"),
            () -> Reference.VERSION,
            (s) -> true,
            (s) -> true
        );

        CHANNEL.registerMessage(
            nextId(),
            ShowConfigGUIMessage.class,
            ShowConfigGUIMessage::write,
            ShowConfigGUIMessage::read,
            ShowConfigGUIMessage.Handler::onMessage
        );
    }
}
