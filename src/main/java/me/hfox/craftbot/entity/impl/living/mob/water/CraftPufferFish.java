package me.hfox.craftbot.entity.impl.living.mob.water;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.water.Cod;
import me.hfox.craftbot.entity.living.mob.water.PufferFish;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftPufferFish extends CraftFishBase implements PufferFish {

    private int puffState;

    public CraftPufferFish(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public int getPuffState() {
        return puffState;
    }

    @Override
    public void setPuffState(int puffState) {
        this.puffState = puffState;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 16) {
            setPuffState(buffer.readVarInt());
        }
    }

}
