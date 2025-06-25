package com.davidmrtns.colossalwonders.block;

import com.davidmrtns.colossalwonders.ColossalWonders;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    public static final Block OPALYNTH_ORE = registerBlock("opalynth_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(1, 3), AbstractBlock.Settings.copy(Blocks.LAPIS_ORE)));
    public static final Block DEEPSLATE_OPALYNTH_ORE = registerBlock("deepslate_opalynth_ore",
            new ExperienceDroppingBlock(UniformIntProvider.create(1, 3), AbstractBlock.Settings.copy(Blocks.DEEPSLATE_LAPIS_ORE)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ColossalWonders.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, new Identifier(ColossalWonders.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        ColossalWonders.LOGGER.info("Registering mod blocks for " + ColossalWonders.MOD_ID);
    }
}
