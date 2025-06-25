package com.davidmrtns.colossalwonders.datagen;

import com.davidmrtns.colossalwonders.block.ModBlocks;
import com.davidmrtns.colossalwonders.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.OPALYNTH_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.DEEPSLATE_OPALYNTH_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.FROZEN_WAND_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SENTIENT_WAND_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SHIELD_WAND_CORE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_OPALYNTH, Models.GENERATED);
        itemModelGenerator.register(ModItems.OPALYNTH_CRYSTAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.OPALYNTH_CRYSTAL_SHARD, Models.GENERATED);
        itemModelGenerator.register(ModItems.OPALYNTH_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.OPALYNTH_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.GRIMOIRE, Models.GENERATED);
    }
}
