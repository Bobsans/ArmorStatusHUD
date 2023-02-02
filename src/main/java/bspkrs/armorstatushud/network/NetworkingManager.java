package bspkrs.armorstatushud.network;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.network.message.ShowConfigScreenMessage;
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
            (version) -> version.equals(Reference.VERSION),
            (version) -> version.equals(Reference.VERSION)
        );

        CHANNEL.registerMessage(nextId(), ShowConfigScreenMessage.class, ShowConfigScreenMessage::encode, ShowConfigScreenMessage::decode, ShowConfigScreenMessage::handle);
    }
}
