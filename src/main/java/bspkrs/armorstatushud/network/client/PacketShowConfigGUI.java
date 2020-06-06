package bspkrs.armorstatushud.network.client;

import bspkrs.armorstatushud.gui.ConfigGUI;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketShowConfigGUI {
    public PacketShowConfigGUI() { }

    public PacketShowConfigGUI(ByteBuf buf) { }

    public void toBytes(ByteBuf buf) { }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(ConfigGUI::open);
        ctx.get().setPacketHandled(true);
    }
}
