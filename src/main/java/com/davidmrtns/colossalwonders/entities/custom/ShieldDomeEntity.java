package com.davidmrtns.colossalwonders.entities.custom;

import com.davidmrtns.colossalwonders.entities.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class ShieldDomeEntity extends Entity {
    private final PlayerEntity owner;
    private int lifetimeTicks = 100; // duration

    public ShieldDomeEntity(EntityType<?> type, World world) {
        super(type, world);
        this.owner = null;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }

    public ShieldDomeEntity(World world, PlayerEntity owner) {
        super(ModEntities.SHIELD_DOME, world);
        this.owner = owner;
        this.noClip = true; // doesn't collide with blocks
        this.setPosition(owner.getX(), owner.getY(), owner.getZ());
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            return;
        }

        if (owner == null || !owner.isAlive() || this.age >= lifetimeTicks) {
            this.discard();
            return;
        }

        // moves with the player
        this.setPosition(owner.getX(), owner.getY(), owner.getZ());

        Box box = this.getBoundingBox().expand(0.5);
        List<Entity> colliding = this.getWorld().getOtherEntities(this, box);

        for (Entity e : colliding) {
            // repels projectiles
            if (e instanceof ProjectileEntity) {
                e.setVelocity(0, 0, 0);
                e.setPosition(this.getX(), this.getY() + 1, this.getZ());
            }else if(isHostileMob(e) && e.squaredDistanceTo(this) < 2.5){
                //doesn't allow hostile mobs to get near
                Vec3d repel = e.getPos().subtract(this.getPos()).normalize().multiply(0.3);
                e.setVelocity(repel.x, 0.1, repel.z);
                e.velocityModified = true;
            }
        }
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) { }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) { }

    private boolean isHostileMob(Entity entity) {
        return entity instanceof MobEntity mob && mob.getTarget() == owner;
    }
}

