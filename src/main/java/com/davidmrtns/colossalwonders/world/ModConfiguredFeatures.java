package com.davidmrtns.colossalwonders.world;

import com.davidmrtns.colossalwonders.ColossalWonders;
import com.davidmrtns.colossalwonders.block.ModBlocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> OPALYNTH_ORE_KEY = registerKey("opalynth_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEEPSLATE_OPALYNTH_ORE_KEY = registerKey("deepslate_opalynth_ore");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context){
        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreFeatureConfig.Target> overworldOpalynthOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.OPALYNTH_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_OPALYNTH_ORE.getDefaultState()));

        register(context, OPALYNTH_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldOpalynthOres, 7));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name){
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(ColossalWonders.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key,
                                                                                   F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
