package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.SkeletonBase;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftSkeletonBase extends CraftMonster implements SkeletonBase {

    public CraftSkeletonBase(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
