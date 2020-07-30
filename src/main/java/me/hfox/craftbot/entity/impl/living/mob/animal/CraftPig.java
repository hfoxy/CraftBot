package me.hfox.craftbot.entity.impl.living.mob.animal;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.animal.Pig;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftPig extends CraftAnimal implements Pig {

    private boolean saddled;
    private int boostTime;

    public CraftPig(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isSaddled() {
        return saddled;
    }

    @Override
    public void setSaddled(boolean saddled) {
        this.saddled = saddled;
    }

    @Override
    public int getBoostTime() {
        return boostTime;
    }

    @Override
    public void setBoostTime(int boostTime) {
        this.boostTime = boostTime;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 16) {
            setSaddled(buffer.readBoolean());
        } else if (index == 17) {
            setBoostTime(buffer.readVarInt());
        }
    }

}
