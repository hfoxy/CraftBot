package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.mob.monster.Evoker;
import me.hfox.craftbot.entity.living.mob.monster.Vindicator;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftEvoker extends CraftSpellcasterIllager implements Evoker {

    public CraftEvoker(World world, int id, UUID uuid, EntityType<? extends Vindicator, ?> entityType) {
        super(world, id, uuid, entityType);
    }

}
