package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;
import java.util.UUID;

public class PacketServerPlaySpawnEntity implements ServerPacket {

    private int id;
    private UUID uuid;
    private int type;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;
    private int data;
    private short velocityX;
    private short velocityY;
    private short velocityZ;

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getType() {
        return type;
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

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public int getData() {
        return data;
    }

    public short getVelocityX() {
        return velocityX;
    }

    public short getVelocityY() {
        return velocityY;
    }

    public short getVelocityZ() {
        return velocityZ;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        id = buffer.readVarInt();
        uuid = buffer.readUuid();
        type = buffer.readVarInt();
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        pitch = buffer.readAngle();
        yaw = buffer.readAngle();
        data = buffer.readInt();
        velocityX = buffer.readShort();
        velocityY = buffer.readShort();
        velocityZ = buffer.readShort();
    }

}
