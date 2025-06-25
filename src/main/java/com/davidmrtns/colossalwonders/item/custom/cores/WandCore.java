package com.davidmrtns.colossalwonders.item.custom.cores;

import com.davidmrtns.colossalwonders.item.custom.WandItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public abstract class WandCore extends Item implements IWandCore {
    public WandCore(Settings settings) {
        super(settings);
    }

    public abstract float performSpell(World world, PlayerEntity user, WandItem wand);
    public abstract float calculateWaste();
}
