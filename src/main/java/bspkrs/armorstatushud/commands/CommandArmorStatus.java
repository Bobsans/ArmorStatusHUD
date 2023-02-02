package bspkrs.armorstatushud.commands;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.network.NetworkingManager;
import bspkrs.armorstatushud.network.message.ShowConfigScreenMessage;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.network.NetworkDirection;


public class CommandArmorStatus {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands
                .literal(Reference.MODID)
                .executes((context) -> showConfig(context.getSource()))
        );
    }

    private static int showConfig(CommandSourceStack source) throws CommandSyntaxException {
        NetworkingManager.CHANNEL.sendTo(
            ShowConfigScreenMessage.INSTANCE,
            source.getPlayerOrException().connection.getConnection(),
            NetworkDirection.PLAY_TO_CLIENT
        );

        return Command.SINGLE_SUCCESS;
    }
}
