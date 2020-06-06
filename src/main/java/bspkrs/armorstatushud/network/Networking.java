package bspkrs.armorstatushud.network;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.network.client.PacketShowConfigGUI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {
    public static SimpleChannel CHANNEL;
    private static int id = 0;

    private static int nextId() {
        return id++;
    }

    public static void registerMessages() {
        CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(Reference.MODID, "ashpacket"), () -> Reference.VERSION, (s) -> true, (s) -> true);

        CHANNEL.registerMessage(nextId(), PacketShowConfigGUI.class, PacketShowConfigGUI::toBytes, PacketShowConfigGUI::new, PacketShowConfigGUI::handle);
    }
}
