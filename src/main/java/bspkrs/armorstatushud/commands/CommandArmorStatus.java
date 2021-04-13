package bspkrs.armorstatushud.commands;

import bspkrs.armorstatushud.Reference;
import bspkrs.armorstatushud.network.NetworkingManager;
import bspkrs.armorstatushud.network.ShowConfigGUIMessage;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.fml.network.NetworkDirection;


public class CommandArmorStatus {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(
            Commands
                .literal(Reference.MODID)
                .executes((context) -> showConfig(context.getSource()))
        );
    }

    private static int showConfig(CommandSource source) throws CommandSyntaxException {
        NetworkingManager.CHANNEL.sendTo(
            new ShowConfigGUIMessage(ShowConfigGUIMessage.SHOW),
            source.getPlayerOrException().connection.getConnection(),
            NetworkDirection.PLAY_TO_CLIENT
        );

        return Command.SINGLE_SUCCESS;
    }
}
