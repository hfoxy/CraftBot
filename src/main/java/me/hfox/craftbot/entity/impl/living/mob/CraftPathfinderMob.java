package me.hfox.craftbot.entity.impl.living.mob;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.PathfinderMob;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftPathfinderMob extends CraftMob implements PathfinderMob {

    public CraftPathfinderMob(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
