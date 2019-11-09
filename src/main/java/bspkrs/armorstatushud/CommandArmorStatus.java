package bspkrs.armorstatushud;

import bspkrs.armorstatushud.fml.gui.GuiASHConfig;
import bspkrs.fml.util.DelayedGuiDisplayTicker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandArmorStatus extends CommandBase {
    @Override
    public String getName() {
        return "armorstatus";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "commands.armorstatus.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        try {
            new DelayedGuiDisplayTicker(10, new GuiASHConfig(null));
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
