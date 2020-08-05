package me.hfox.craftbot.entity.impl.living.mob.tameable;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.data.CatType;
import me.hfox.craftbot.entity.living.LivingEntity;
import me.hfox.craftbot.entity.living.mob.animal.tameable.Cat;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftCat extends CraftTameable implements Cat {

    private CatType type;
    private boolean unknownBooleanA;
    private boolean unknownBooleanB;
    private int collarColour;

    public CraftCat(World world, int id, UUID uuid, EntityType<? extends LivingEntity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public CatType getType() {
        return type;
    }

    @Override
    public void setType(CatType type) {
        this.type = type;
    }

    @Override
    public boolean getUnknownBooleanA() {
        return unknownBooleanA;
    }

    @Override
    public void setUnknownBooleanA(boolean unknownBooleanA) {
        this.unknownBooleanA = unknownBooleanA;
    }

    @Override
    public boolean getUnknownBooleanB() {
        return unknownBooleanB;
    }

    @Override
    public void setUnknownBooleanB(boolean unknownBooleanB) {
        this.unknownBooleanB = unknownBooleanB;
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
            setType(CatType.values()[buffer.readVarInt()]);
        } else if (index == 19) {
            setUnknownBooleanA(buffer.readBoolean());
        } else if (index == 20) {
            setUnknownBooleanB(buffer.readBoolean());
        } else if (index == 21) {
            setCollarColour(buffer.readVarInt());
        }
    }

}
