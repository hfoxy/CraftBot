package me.hfox.craftbot.entity.impl.living.mob.flying;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.mob.CraftMob;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.flying.Flying;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftFlying extends CraftMob implements Flying {

    public CraftFlying(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
