package com.davidmrtns.colossalwonders.item;

import com.davidmrtns.colossalwonders.ColossalWonders;
import com.davidmrtns.colossalwonders.components.ModComponents;
import com.davidmrtns.colossalwonders.item.custom.GrimoireItem;
import com.davidmrtns.colossalwonders.item.custom.WandItem;
import com.davidmrtns.colossalwonders.item.custom.cores.FrozenWandCoreItem;
import com.davidmrtns.colossalwonders.item.custom.cores.SentientWandCoreItem;
import com.davidmrtns.colossalwonders.item.custom.cores.ShieldWandCoreItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item WAND = registerItem("wand", new WandItem(new Item.Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT).component(ModComponents.WAND_ENERGY, 100f)));
    public static final Item GRIMOIRE = registerItem("grimoire", new GrimoireItem(new Item.Settings().maxCount(1)));
    public static final Item SENTIENT_WAND_CORE = registerItem("sentient_wand_core", new SentientWandCoreItem(new Item.Settings().maxCount(1)));
    public static final Item FROZEN_WAND_CORE = registerItem("frozen_wand_core", new FrozenWandCoreItem(new Item.Settings().maxCount(1)));
    public static final Item SHIELD_WAND_CORE = registerItem("shield_wand_core", new ShieldWandCoreItem(new Item.Settings().maxCount(1)));
    public static final Item RAW_OPALYNTH = registerItem("raw_opalynth", new Item(new Item.Settings()));
    public static final Item OPALYNTH_CRYSTAL = registerItem("opalynth_crystal", new Item(new Item.Settings()));
    public static final Item OPALYNTH_CRYSTAL_SHARD = registerItem("opalynth_crystal_shard", new Item(new Item.Settings()));
    public static final Item OPALYNTH_DUST = registerItem("opalynth_dust", new Item(new Item.Settings()));
    public static final Item OPALYNTH_INGOT = registerItem("opalynth_ingot", new Item(new Item.Settings()));

    private static void addItemsToIngredientsItemGroup(FabricItemGroupEntries entries){
        entries.add(RAW_OPALYNTH);
        entries.add(OPALYNTH_CRYSTAL);
        entries.add(OPALYNTH_CRYSTAL_SHARD);
        entries.add(OPALYNTH_DUST);
        entries.add(OPALYNTH_INGOT);
    }

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(ColossalWonders.MOD_ID, name), item);
    }

    public static void registerModItems(){
        ColossalWonders.LOGGER.info("Registering mod items for " + ColossalWonders.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientsItemGroup);
    }
}
