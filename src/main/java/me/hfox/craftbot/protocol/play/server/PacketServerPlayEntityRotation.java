package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketServerPlayEntityRotation implements ServerPacket {

    private int entityId;
    private float yaw;
    private float pitch;
    private boolean grounded;

    public int getEntityId() {
        return entityId;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isGrounded() {
        return grounded;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        entityId = buffer.readVarInt();
        yaw = buffer.readAngle();
        pitch = buffer.readAngle();
        grounded = buffer.readBoolean();
    }

}
