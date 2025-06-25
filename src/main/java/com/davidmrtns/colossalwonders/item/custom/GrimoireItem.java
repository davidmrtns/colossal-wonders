package com.davidmrtns.colossalwonders.item.custom;

import com.davidmrtns.colossalwonders.networking.OpenGrimoireScreenS2CPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GrimoireItem extends Item {
    public GrimoireItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user instanceof ServerPlayerEntity serverPlayer) {
            OpenGrimoireScreenS2CPayload payload = new OpenGrimoireScreenS2CPayload(1);
            ServerPlayNetworking.send(serverPlayer, payload);
            System.out.println("Grimoire screen packet sent to " + serverPlayer.getName().getString());
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
