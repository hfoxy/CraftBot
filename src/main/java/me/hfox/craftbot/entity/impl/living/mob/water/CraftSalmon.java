package me.hfox.craftbot.entity.impl.living.mob.water;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.water.Cod;
import me.hfox.craftbot.entity.living.mob.water.Salmon;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftSalmon extends CraftFishBase implements Salmon {

    public CraftSalmon(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
