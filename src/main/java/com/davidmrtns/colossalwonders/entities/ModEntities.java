package com.davidmrtns.colossalwonders.entities;

import com.davidmrtns.colossalwonders.ColossalWonders;
import com.davidmrtns.colossalwonders.entities.custom.ShieldDomeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<ShieldDomeEntity> SHIELD_DOME = registerEntity(
            "shield_dome",
            FabricEntityTypeBuilder.<ShieldDomeEntity>create(SpawnGroup.MISC, ShieldDomeEntity::new)
                    .dimensions(EntityDimensions.fixed(3.0f, 3.0f)) // size
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(1)
                    .fireImmune()
                    .build()
    );

    private static <T extends net.minecraft.entity.Entity> EntityType<T> registerEntity(String name, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(ColossalWonders.MOD_ID, name), type);
    }

    public static void registerModEntities() {
        ColossalWonders.LOGGER.info("Registering mod entities for " + ColossalWonders.MOD_ID);
    }
}

