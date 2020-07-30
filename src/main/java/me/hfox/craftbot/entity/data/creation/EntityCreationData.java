package me.hfox.craftbot.entity.data.creation;

import com.google.common.base.Preconditions;
import me.hfox.craftbot.world.World;

import java.util.UUID;

public class EntityCreationData {

    private final World world;
    private final int entityId;
    private final UUID uuid;

    public EntityCreationData(World world, int entityId, UUID uuid) {
        Preconditions.checkNotNull(world, "world cannot be null");
        Preconditions.checkNotNull(uuid, "uuid cannot be null");

        this.world = world;
        this.entityId = entityId;
        this.uuid = uuid;
    }

    public World getWorld() {
        return world;
    }

    public int getEntityId() {
        return entityId;
    }

    public UUID getUuid() {
        return uuid;
    }

}
