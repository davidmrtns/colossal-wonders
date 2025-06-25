package com.davidmrtns.colossalwonders.screens;

import com.davidmrtns.colossalwonders.item.custom.WandItem;
import com.davidmrtns.colossalwonders.item.custom.cores.WandCore;
import com.davidmrtns.colossalwonders.keybindings.ModKeyBindings;
import com.davidmrtns.colossalwonders.networking.DropWandCoreC2SPayload;
import com.davidmrtns.colossalwonders.networking.SwapWandCoreC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class RadialMenuOverlay {
    private record RadialMenuItem(ItemStack itemStack, int slot) {}

    private static boolean isActive = false;
    private static final int RADIUS = 60;
    private static final List<RadialMenuItem> options = new ArrayList<>();

    public static void init() {
        HudRenderCallback.EVENT.register(RadialMenuOverlay::render);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.getWindow() == null) return;
            if (client.currentScreen != null) return;

            boolean keyHeld = ModKeyBindings.CHANGE_WAND_CORE.isPressed();
            boolean stackIsWand = client.player.getMainHandStack().getItem() instanceof WandItem;

            if (keyHeld && !isActive && stackIsWand) {
                if(InputUtil.isKeyPressed(
                        client.getWindow().getHandle(),
                        GLFW.GLFW_KEY_LEFT_SHIFT
                )) {
                    DropWandCoreC2SPayload payload = new DropWandCoreC2SPayload(1);
                    ClientPlayNetworking.send(payload);
                }else{
                    openMenu(client);
                }
            } else if (!keyHeld && isActive) {
                selectWandCore(client);
                closeMenu();
            }
        });
    }

    private static void openMenu(MinecraftClient client) {
        if (isActive) return;

        options.clear();

        int slot = 0;
        for (ItemStack stack : client.player.getInventory().main) {
            if (stack.getItem() instanceof WandCore && !options.contains(stack)) {
                options.add(new RadialMenuItem(stack, slot));
            }
            slot++;
        }

        client.mouse.unlockCursor();
        isActive = true;
    }

    private static void closeMenu() {
        MinecraftClient.getInstance().mouse.lockCursor();
        isActive = false;
    }

    private static void selectWandCore(MinecraftClient client) {
        if (options.isEmpty()) return;

        int centerX = client.getWindow().getScaledWidth() / 2;
        int centerY = client.getWindow().getScaledHeight() / 2;

        double mouseX = client.mouse.getX() / client.getWindow().getScaleFactor();
        double mouseY = client.mouse.getY() / client.getWindow().getScaleFactor();

        double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
        if (angle < 0) angle += 2 * Math.PI;

        int selected = (int) ((angle / (2 * Math.PI)) * options.size());
        ItemStack chosen = options.get(selected).itemStack;

        System.out.println("Selected wand core: " + chosen.getName().getString());

        ItemStack wandStack = client.player.getMainHandStack();
        if (wandStack.getItem() instanceof WandItem && chosen != null) {
            int slot = options.get(selected).slot;

            SwapWandCoreC2SPayload payload = new SwapWandCoreC2SPayload(slot);
            ClientPlayNetworking.send(payload);
            System.out.println("Packet sent");
        }
    }

    public static void render(DrawContext context, float tickDelta) {
        if (!isActive) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int centerX = client.getWindow().getScaledWidth() / 2;
        int centerY = client.getWindow().getScaledHeight() / 2;

        double mouseX = client.mouse.getX() / client.getWindow().getScaleFactor();
        double mouseY = client.mouse.getY() / client.getWindow().getScaleFactor();
        double mouseAngle = Math.atan2(mouseY - centerY, mouseX - centerX);
        if (mouseAngle < 0) mouseAngle += 2 * Math.PI;

        int optionCount = options.size();
        double anglePerOption = 2 * Math.PI / optionCount;

        for (int i = 0; i < optionCount; i++) {
            double angle = anglePerOption * i;
            double diff = Math.abs(mouseAngle - angle);
            if (diff > Math.PI) diff = 2 * Math.PI - diff;

            boolean isHovered = diff < (anglePerOption / 2.5);

            int itemX = centerX + (int) (RADIUS * Math.cos(angle)) - 8;
            int itemY = centerY + (int) (RADIUS * Math.sin(angle)) - 8;

            ItemStack stack = options.get(i).itemStack;

            context.getMatrices().push();

            if (isHovered) {
                // zooms the hovered core
                context.getMatrices().translate(itemX + 8, itemY + 8, 0);
                context.getMatrices().scale(1.5f, 1.5f, 1.0f);
                context.getMatrices().translate(-8, -8, 0);
                context.drawItem(stack, 0, 0);
                context.drawItemInSlot(client.textRenderer, stack, 0, 0);
            } else {
                context.drawItem(stack, itemX, itemY);
                context.drawItemInSlot(client.textRenderer, stack, itemX, itemY);
            }

            context.getMatrices().pop();
        }
    }
}
