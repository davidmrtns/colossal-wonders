package com.davidmrtns.colossalwonders.item.custom.cores;

import com.davidmrtns.colossalwonders.item.custom.WandItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class SentientWandCoreItem extends WandCore {
    public SentientWandCoreItem(Settings settings) {
        super(settings);
    }

    @Override
    public float performSpell(World world, PlayerEntity user, WandItem wand) {

        final Predicate<LivingEntity> VALID_CREATURE = (livingEntity) -> !(livingEntity instanceof PlayerEntity);

        Box box = (new Box(user.getX(), user.getY(), user.getZ(),
                user.getX() + 1, user.getY() + 1, user.getZ() + 1))
                .expand(15).stretch(0.0D, 1.5D, 0.0D);

        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, VALID_CREATURE);
        Iterator var1 = list.iterator();

        LivingEntity entity;
        while (var1.hasNext()){
            entity = (LivingEntity) var1.next();
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 20*6));
        }

        return calculateWaste();
    }

    @Override
    public float calculateWaste() {
        Random disccount = new Random();
        return disccount.nextInt(2) * 3.9f;
    }
}
