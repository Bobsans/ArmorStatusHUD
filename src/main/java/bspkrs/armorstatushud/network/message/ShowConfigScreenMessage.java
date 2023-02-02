package bspkrs.armorstatushud.network.message;

import bspkrs.armorstatushud.gui.ConfigScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@SuppressWarnings("InstantiationOfUtilityClass")
public class ShowConfigScreenMessage {
    public static ShowConfigScreenMessage INSTANCE = new ShowConfigScreenMessage();

    public static ShowConfigScreenMessage decode(FriendlyByteBuf buffer) {
        return INSTANCE;
    }

    public static void encode(ShowConfigScreenMessage message, FriendlyByteBuf buffer) {}

    public static void handle(ShowConfigScreenMessage message, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(ConfigScreen::open);
        context.get().setPacketHandled(true);
    }
}
