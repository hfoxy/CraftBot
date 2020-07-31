package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayEntityPosition implements ServerPacket {

    private int entityId;
    private short deltaX;
    private short deltaY;
    private short deltaZ;
    private boolean grounded;

    public int getEntityId() {
        return entityId;
    }

    public double getDeltaX() {
        return deltaX / (128D * 32);
    }

    public double getDeltaY() {
        return deltaY / (128D * 32);
    }

    public double getDeltaZ() {
        return deltaZ / (128D * 32);
    }

    public boolean isGrounded() {
        return grounded;
    }

    protected void readYawPitch(ProtocolBuffer buffer) {
        // nothing here
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        deltaX = buffer.readShort();
        deltaY = buffer.readShort();
        deltaZ = buffer.readShort();
        readYawPitch(buffer);
        grounded = buffer.readBoolean();
    }

}
