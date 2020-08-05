package me.hfox.craftbot.entity.impl.living.mob.tameable;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.CatType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.animal.tameable.Cat;
import me.hfox.craftbot.entity.living.mob.animal.tameable.Wolf;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

public class CraftWolf extends CraftTameable implements Wolf {

    private boolean begging;
    private int collarColour;

    public CraftWolf(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isBegging() {
        return begging;
    }

    @Override
    public void setBegging(boolean begging) {
        this.begging = begging;
    }

    @Override
    public int getCollarColour() {
        return collarColour;
    }

    @Override
    public void setCollarColour(int collarColour) {
        this.collarColour = collarColour;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 18) {
            setBegging(buffer.readBoolean());
        } else if (index == 19) {
            setCollarColour(buffer.readVarInt());
        }
    }

}
