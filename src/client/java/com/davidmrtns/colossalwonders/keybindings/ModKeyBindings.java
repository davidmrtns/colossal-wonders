package com.davidmrtns.colossalwonders.keybindings;

import com.davidmrtns.colossalwonders.ColossalWonders;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final KeyBinding CHANGE_WAND_CORE = registerKeyBinding("key.colossal-wonders.change_wand_core", GLFW.GLFW_KEY_R);

    private static KeyBinding registerKeyBinding(String name, int code) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(name, code, "Colossal Wonders"));
    }

    public static void registerModKeyBindings(){
        ColossalWonders.LOGGER.info("Registering mod keybindings for " + ColossalWonders.MOD_ID);
    }
}
