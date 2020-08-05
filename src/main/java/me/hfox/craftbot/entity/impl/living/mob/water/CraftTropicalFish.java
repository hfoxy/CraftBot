package me.hfox.craftbot.entity.impl.living.mob.water;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.water.PufferFish;
import me.hfox.craftbot.entity.living.mob.water.TropicalFish;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftTropicalFish extends CraftFishBase implements TropicalFish {

    private int variant;

    public CraftTropicalFish(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public int getVariant() {
        return variant;
    }

    @Override
    public void setVariant(int variant) {
        this.variant = variant;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 16) {
            setVariant(buffer.readVarInt());
        }
    }

}
