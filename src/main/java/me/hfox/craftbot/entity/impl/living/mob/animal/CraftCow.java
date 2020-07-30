package me.hfox.craftbot.entity.impl.living.mob.animal;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.animal.Chicken;
import me.hfox.craftbot.entity.living.mob.animal.Cow;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftCow extends CraftAnimal implements Cow {

    public CraftCow(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
