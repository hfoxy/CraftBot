package me.hfox.craftbot.entity.impl.projectile;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.entity.impl.CraftEntity;
import me.hfox.craftbot.entity.projectile.Arrow;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.World;

import java.io.IOException;
import java.util.UUID;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class CraftArrow extends CraftEntity implements Arrow {

    private boolean critical;
    private boolean noClip;
    private byte piercingLevel;
    private int colour;

    public CraftArrow(World world, int id, UUID uuid, EntityType<? extends Entity, ?> entityType) {
        super(world, id, uuid, entityType);
    }

    @Override
    public boolean isCritical() {
        return critical;
    }

    @Override
    public void setCritical(boolean critical) {
        this.critical = critical;
    }

    @Override
    public boolean isNoClip() {
        return noClip;
    }

    @Override
    public void setNoClip(boolean noClip) {
        this.noClip = noClip;
    }

    @Override
    public byte getPiercingLevel() {
        return piercingLevel;
    }

    @Override
    public void setPiercingLevel(byte piercingLevel) {
        this.piercingLevel = piercingLevel;
    }

    @Override
    public int getColour() {
        return colour;
    }

    @Override
    public void setColour(int colour) {
        this.colour = colour;
    }

    @Override
    public void readMetadata(EntityMetadata metadata) throws IOException {
        super.readMetadata(metadata);

        int index = metadata.getIndex();
        ProtocolBuffer buffer = metadata.getBufferValue();

        if (index == 7) {
            byte flags = buffer.readByte();
            setCritical(hasFlag(flags, 0x01));
            setNoClip(hasFlag(flags, 0x02));
        } else if (index == 8) {
            setPiercingLevel(buffer.readByte());
        } else if (index == 9) {
            setColour(buffer.readVarInt());
        }
    }

}
