package bspkrs.armorstatushud.binding;

import bspkrs.armorstatushud.Reference;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBindings {
    public static final KeyMapping OPEN_SETTINGS = new KeyMapping(
        Reference.MODID + ".keys.open_settings",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM,
        InputConstants.KEY_MULTIPLY,
        Reference.MODID + ".keys.group"
    );
}
