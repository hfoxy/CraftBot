package me.hfox.craftbot.entity.impl;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.FallingBlock;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftFallingBlock extends CraftEntity implements FallingBlock {

    private Location spawnPosition;

    public CraftFallingBlock(World world, int id, UUID uuid, EntityType<? extends Entity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public Location getSpawnPosition() {
        return spawnPosition;
    }

    @Override
    public void setSpawnPosition(Location spawnPosition) {
        this.spawnPosition = spawnPosition;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 7) {
            setSpawnPosition(buffer.readPosition());
        }
    }

}
