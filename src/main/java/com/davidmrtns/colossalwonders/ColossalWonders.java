package com.davidmrtns.colossalwonders;

import com.davidmrtns.colossalwonders.block.ModBlocks;
import com.davidmrtns.colossalwonders.components.ModComponents;
import com.davidmrtns.colossalwonders.item.ModItemGroups;
import com.davidmrtns.colossalwonders.item.ModItems;
import com.davidmrtns.colossalwonders.item.custom.WandItem;
import com.davidmrtns.colossalwonders.entities.ModEntities;
import com.davidmrtns.colossalwonders.networking.DropWandCoreC2SPayload;
import com.davidmrtns.colossalwonders.networking.OpenGrimoireScreenS2CPayload;
import com.davidmrtns.colossalwonders.networking.SwapWandCoreC2SPayload;
import com.davidmrtns.colossalwonders.world.gen.ModWorldGeneration;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.davidmrtns.colossalwonders.item.custom.WandItem.dropWandCore;
import static com.davidmrtns.colossalwonders.item.custom.WandItem.swapWandCore;

public class ColossalWonders implements ModInitializer {
	public static final String MOD_ID = "colossal_wonders";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModComponents.registerModComponents();
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModWorldGeneration.generateModWorldGen();
		ModEntities.registerModEntities();

		PayloadTypeRegistry.playC2S().register(SwapWandCoreC2SPayload.ID, SwapWandCoreC2SPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(DropWandCoreC2SPayload.ID, DropWandCoreC2SPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(OpenGrimoireScreenS2CPayload.ID, OpenGrimoireScreenS2CPayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(SwapWandCoreC2SPayload.ID, (payload, context) -> {
			ServerPlayerEntity player = context.player();
			if(player.getMainHandStack().getItem() instanceof WandItem){
				ItemStack wandStack = context.player().getMainHandStack();
				int selectedFocusSlot = payload.slot();
				swapWandCore(player, wandStack, selectedFocusSlot);
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(DropWandCoreC2SPayload.ID, (payload, context) -> {
			ServerPlayerEntity player = context.player();
			if(player.getMainHandStack().getItem() instanceof WandItem){
				ItemStack wandStack = context.player().getMainHandStack();
				dropWandCore(player, wandStack);
			}
		});
	}
}