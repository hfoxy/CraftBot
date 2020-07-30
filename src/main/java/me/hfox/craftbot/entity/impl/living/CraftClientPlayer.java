package me.hfox.craftbot.entity.impl.living;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class CraftClientPlayer extends CraftPlayer {

    public CraftClientPlayer(World world, int id, UUID uuid, EntityType<? extends CraftPlayer, ?> entityType, PlayerInfo info) {
        super(world, id, uuid, entityType, info);
    }

}
