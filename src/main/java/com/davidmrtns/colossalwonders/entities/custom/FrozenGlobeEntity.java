package com.davidmrtns.colossalwonders.entities.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class FrozenGlobeEntity extends ThrownItemEntity {
    public FrozenGlobeEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public FrozenGlobeEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    public FrozenGlobeEntity(World world, double x, double y, double z) {
        super(EntityType.SNOWBALL, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();

        entity.damage(this.getDamageSources().freeze(), 0.5F);
        entity.setFrozenTicks(400);
    }
}
