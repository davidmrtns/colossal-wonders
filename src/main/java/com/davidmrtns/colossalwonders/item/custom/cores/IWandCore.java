package com.davidmrtns.colossalwonders.item.custom.cores;

import com.davidmrtns.colossalwonders.item.custom.WandItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IWandCore {
    public abstract float performSpell(World world, PlayerEntity user, WandItem wand);
    public abstract float calculateWaste();
}
