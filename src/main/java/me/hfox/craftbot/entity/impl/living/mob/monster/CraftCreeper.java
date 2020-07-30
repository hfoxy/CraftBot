package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.Creeper;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftCreeper extends CraftMonster implements Creeper {

    private boolean fused;
    private boolean charged;
    private boolean ignited;

    public CraftCreeper(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isFused() {
        return fused;
    }

    @Override
    public void setFused(boolean fused) {
        this.fused = fused;
    }

    @Override
    public boolean isCharged() {
        return charged;
    }

    @Override
    public void setCharged(boolean charged) {
        this.charged = charged;
    }

    @Override
    public boolean isIgnited() {
        return ignited;
    }

    @Override
    public void setIgnited(boolean ignited) {
        this.ignited = ignited;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 15) {
            setFused(buffer.readVarInt() == 1);
        } else if (index == 16) {
            setCharged(buffer.readBoolean());
        } else if (index == 17) {
            setIgnited(buffer.readBoolean());
        }
    }

}
