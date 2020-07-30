package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Velocity;

import java.io.IOException;
import java.util.UUID;

public class PacketServerPlaySpawnEntity implements ServerPacket {

    protected int id;
    protected UUID uuid;
    protected int type;
    protected double x;
    protected double y;
    protected double z;
    protected float pitch;
    protected float yaw;
    protected int data;
    protected Velocity velocity;

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

    public Velocity getVelocity() {
        return velocity;
    }

    protected void readInfoAndXYZ(ProtocolBuffer buffer) {
        id = buffer.readVarInt();
        uuid = buffer.readUuid();
        type = buffer.readVarInt();
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        readInfoAndXYZ(buffer);
        pitch = buffer.readAngle();
        yaw = buffer.readAngle();
        data = buffer.readInt();
        velocity = new Velocity(buffer.readShort(), buffer.readShort(), buffer.readShort());
    }

}
