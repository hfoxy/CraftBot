package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.mob.CraftPathfinderMob;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.Monster;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftMonster extends CraftPathfinderMob implements Monster {

    public CraftMonster(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
