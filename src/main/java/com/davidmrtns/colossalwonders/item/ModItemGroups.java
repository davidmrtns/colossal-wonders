package com.davidmrtns.colossalwonders.item;

import com.davidmrtns.colossalwonders.ColossalWonders;
import com.davidmrtns.colossalwonders.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;


public class ModItemGroups {
    public static final ItemGroup COLOSSAL_WONDERS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ColossalWonders.MOD_ID, "colossal_wonders"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.colossal_wonders"))
                    .icon(() -> new ItemStack(ModItems.GRIMOIRE)).entries((displayContext, entries) -> {
                        entries.add(ModItems.OPALYNTH_CRYSTAL);
                        entries.add(ModItems.OPALYNTH_CRYSTAL_SHARD);
                        entries.add(ModItems.OPALYNTH_DUST);
                        entries.add(ModItems.OPALYNTH_INGOT);
                        entries.add(ModItems.RAW_OPALYNTH);
                        entries.add(ModItems.WAND);
                        entries.add(ModItems.SENTIENT_WAND_CORE);
                        entries.add(ModItems.FROZEN_WAND_CORE);
                        entries.add(ModItems.SHIELD_WAND_CORE);
                        entries.add(ModItems.GRIMOIRE);
                        entries.add(ModBlocks.OPALYNTH_ORE);
                        entries.add(ModBlocks.DEEPSLATE_OPALYNTH_ORE);
                    }).build());

    public static void registerItemGroups(){
        ColossalWonders.LOGGER.info("Registering item groups for " + ColossalWonders.MOD_ID);
    }
}
