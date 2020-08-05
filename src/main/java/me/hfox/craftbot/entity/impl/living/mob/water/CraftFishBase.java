package me.hfox.craftbot.entity.impl.living.mob.water;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.water.FishBase;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftFishBase extends CraftWaterAnimal implements FishBase {

    private boolean fromBucket;

    public CraftFishBase(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isFromBucket() {
        return fromBucket;
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.fromBucket = fromBucket;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 15) {
            setFromBucket(buffer.readBoolean());
        }
    }

}
