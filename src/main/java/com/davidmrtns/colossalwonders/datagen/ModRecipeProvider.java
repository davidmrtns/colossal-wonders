package com.davidmrtns.colossalwonders.datagen;

import com.davidmrtns.colossalwonders.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    private static final List<ItemConvertible> OPALYNTH_SMELTABLES = List.of(
            ModItems.RAW_OPALYNTH
    );

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter, OPALYNTH_SMELTABLES, RecipeCategory.MISC, ModItems.OPALYNTH_CRYSTAL,
                0.7f, 200, "ruby");
        offerBlasting(exporter, OPALYNTH_SMELTABLES, RecipeCategory.MISC, ModItems.OPALYNTH_CRYSTAL,
                0.7f, 100, "ruby");
        offerShapelessRecipe(exporter, ModItems.OPALYNTH_CRYSTAL_SHARD, ModItems.OPALYNTH_CRYSTAL, "opalynth", 9);
        offerShapelessRecipe(exporter, ModItems.OPALYNTH_DUST, ModItems.OPALYNTH_CRYSTAL_SHARD, "opalynth", 1);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.OPALYNTH_INGOT, 1)
                .pattern("vvv")
                .pattern("v#v")
                .pattern("vvv")
                .input('v', ModItems.OPALYNTH_CRYSTAL_SHARD)
                .input('#', Items.IRON_INGOT)
                .criterion(hasItem(ModItems.OPALYNTH_CRYSTAL_SHARD), conditionsFromItem(ModItems.OPALYNTH_CRYSTAL_SHARD))
                .criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.OPALYNTH_INGOT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAND, 1)
                .pattern("  #")
                .pattern(" / ")
                .pattern("-  ")
                .input('#', ModItems.OPALYNTH_INGOT)
                .input('/', Items.STICK)
                .input('-', TagKey.of(RegistryKeys.ITEM, new Identifier("minecraft", "wooden_buttons")))
                .criterion(hasItem(ModItems.OPALYNTH_INGOT), conditionsFromItem(ModItems.OPALYNTH_INGOT))
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WAND)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.GRIMOIRE, 1)
                .pattern("***")
                .pattern("*#*")
                .pattern("***")
                .input('*', ModItems.OPALYNTH_DUST)
                .input('#', Items.BOOK)
                .criterion(hasItem(ModItems.OPALYNTH_DUST), conditionsFromItem(ModItems.OPALYNTH_DUST))
                .criterion(hasItem(Items.BOOK), conditionsFromItem(Items.BOOK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.GRIMOIRE)));
    }
}
