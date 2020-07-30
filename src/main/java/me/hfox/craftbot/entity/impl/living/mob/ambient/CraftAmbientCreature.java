package me.hfox.craftbot.entity.impl.living.mob.ambient;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.mob.CraftMob;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.ambient.AmbientCreature;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftAmbientCreature extends CraftMob implements AmbientCreature {

    public CraftAmbientCreature(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
