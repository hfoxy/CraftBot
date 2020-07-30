package me.hfox.craftbot.entity.impl.living.mob.animal;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.mob.CraftAgeableMob;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.animal.Animal;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftAnimal extends CraftAgeableMob implements Animal {

    public CraftAnimal(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
