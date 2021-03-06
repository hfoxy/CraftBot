package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;
import java.util.UUID;

public class PacketServerPlayEntityTeleport implements ServerPacket {

    private int entityId;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean grounded;

    public int getEntityId() {
        return entityId;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
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
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        yaw = buffer.readAngle();
        pitch = buffer.readAngle();
        grounded = buffer.readBoolean();
    }

}
