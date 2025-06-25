package com.davidmrtns.colossalwonders.item.custom.cores;

import com.davidmrtns.colossalwonders.item.custom.WandItem;
import com.davidmrtns.colossalwonders.entities.custom.ShieldDomeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

import java.util.Random;

public class ShieldWandCoreItem extends WandCore {
    public ShieldWandCoreItem(Settings settings) {
        super(settings);
    }

    @Override
    public float performSpell(World world, PlayerEntity user, WandItem wand) {
        if (!world.isClient) {
            ShieldDomeEntity dome = new ShieldDomeEntity(world, user);
            world.spawnEntity(dome);
            user.getItemCooldownManager().set(wand, 200); // cooldown
        }
        return calculateWaste();
    }

    @Override
    public float calculateWaste() {
        Random disccount = new Random();
        return disccount.nextInt(2) * 3.9f;
    }
}
