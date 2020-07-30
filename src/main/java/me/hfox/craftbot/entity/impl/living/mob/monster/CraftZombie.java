package me.hfox.craftbot.entity.impl.living.mob.monster;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.living.CraftLivingEntity;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.monster.Zombie;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftZombie extends CraftMonster implements Zombie {

    private boolean baby;
    private int type;
    private boolean becomingDrowned;

    public CraftZombie(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isBaby() {
        return baby;
    }

    @Override
    public void setBaby(boolean baby) {
        this.baby = baby;
    }

    @Override
    public boolean isBecomingDrowned() {
        return becomingDrowned;
    }

    @Override
    public void setBecomingDrowned(boolean becomingDrowned) {
        this.becomingDrowned = becomingDrowned;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 15) {
            setBaby(buffer.readBoolean());
        } else if (index == 16) {
            // unused
            // entity.setTypeId(buffer.readVarInt());
        } else if (index == 17) {
            setBecomingDrowned(buffer.readBoolean());
        }
    }

}
