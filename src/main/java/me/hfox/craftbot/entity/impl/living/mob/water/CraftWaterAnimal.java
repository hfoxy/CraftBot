package me.hfox.craftbot.entity.impl.living.mob.water;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.mob.CraftPathfinderMob;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.water.WaterAnimal;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftWaterAnimal extends CraftPathfinderMob implements WaterAnimal {

    public CraftWaterAnimal(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
