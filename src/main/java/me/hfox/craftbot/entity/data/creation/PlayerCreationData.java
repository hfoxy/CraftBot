package me.hfox.craftbot.entity.data.creation;

import me.hfox.craftbot.entity.data.PlayerInfo;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class PlayerCreationData extends EntityCreationData {

    private final PlayerInfo info;

    public PlayerCreationData(World world, int entityId, UUID uuid, PlayerInfo info) {
        super(world, entityId, uuid);
        this.info = info;
    }

    public PlayerInfo getInfo() {
        return info;
    }

}
