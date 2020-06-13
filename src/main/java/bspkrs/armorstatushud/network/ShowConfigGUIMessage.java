package bspkrs.armorstatushud.network;

import bspkrs.armorstatushud.gui.ConfigGUI;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ShowConfigGUIMessage {
    public static byte SHOW = 1;

    protected byte data;

    public ShowConfigGUIMessage(byte data) {
        this.data = data;
    }

    public static ShowConfigGUIMessage read(PacketBuffer buffer) {
        return new ShowConfigGUIMessage(buffer.readByte());
    }

    public static void write(ShowConfigGUIMessage message, PacketBuffer buffer) {
        buffer.writeByte(message.data);
    }

    public static class Handler {
        public static void onMessage(ShowConfigGUIMessage message, Supplier<NetworkEvent.Context> context) {
            context.get().enqueueWork(ConfigGUI::open);
            context.get().setPacketHandled(true);
        }
    }
}
