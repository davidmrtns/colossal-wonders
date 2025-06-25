package com.davidmrtns.colossalwonders.components;

import com.davidmrtns.colossalwonders.ColossalWonders;
import com.mojang.serialization.Codec;
import net.minecraft.component.DataComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
    public static final DataComponentType<Float> WAND_ENERGY = registerComponent("wand_energy", Codec.FLOAT);

    public static <T> DataComponentType<T> registerComponent(String name, Codec<T> codec) {
        DataComponentType<T> component = DataComponentType.<T>builder()
                .codec(codec)
                .build();

        return Registry.register(
                Registries.DATA_COMPONENT_TYPE,
                Identifier.of(ColossalWonders.MOD_ID, name),
                component
        );
    }

    public static void registerModComponents() {
        ColossalWonders.LOGGER.info("Registering mod components for " + ColossalWonders.MOD_ID);
    }
}
