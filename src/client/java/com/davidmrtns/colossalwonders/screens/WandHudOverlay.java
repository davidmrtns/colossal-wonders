package com.davidmrtns.colossalwonders.screens;

import com.davidmrtns.colossalwonders.item.custom.WandItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class WandHudOverlay {

    public static void render(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.player == null) return;

        ItemStack stack = client.player.getMainHandStack();

        if (!(stack.getItem() instanceof WandItem)) return;

        ItemStack storedFocus = WandItem.getActiveWandCore(stack);
        if (storedFocus == null || storedFocus.isEmpty()) return;

        // HUD coordinates
        int x = 10;
        int y = 10;

        // renders the wand core
        context.drawItem(storedFocus, x, y);
        context.drawItemInSlot(MinecraftClient.getInstance().textRenderer, storedFocus, x, y);
    }
}

