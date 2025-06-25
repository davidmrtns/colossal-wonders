package com.davidmrtns.colossalwonders.item.custom.cores;

import com.davidmrtns.colossalwonders.item.custom.WandItem;
import com.davidmrtns.colossalwonders.entities.custom.FrozenGlobeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Random;

public class FrozenWandCoreItem extends WandCore {
    public FrozenWandCoreItem(Settings settings) {
        super(settings);
    }

    @Override
    public float performSpell(World world, PlayerEntity user, WandItem wand) {
        if (!world.isClient) {
            FrozenGlobeEntity frozenGlobeEntity = new FrozenGlobeEntity(world, user);
            frozenGlobeEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(frozenGlobeEntity);
        }

        user.getItemCooldownManager().set(wand, 20); //cooldown
        return calculateWaste();
    }

    @Override
    public float calculateWaste() {
        Random disccount = new Random();
        return disccount.nextInt(2) * 3.9f;
    }
}
