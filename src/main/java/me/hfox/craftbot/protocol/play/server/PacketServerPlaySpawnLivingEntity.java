package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.entity.EntityType;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Velocity;

import java.io.IOException;
import java.util.UUID;

public class PacketServerPlaySpawnLivingEntity implements ServerPacket {

    private int id;
    private UUID uuid;
    private EntityType type;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private float headPitch;
    private Velocity velocity;

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public EntityType getType() {
        // return type;
        throw new UnsupportedOperationException();
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

    public float getHeadPitch() {
        return headPitch;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        id = buffer.readVarInt();
        uuid = buffer.readUuid();
        buffer.readVarInt(); // type = null;
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        yaw = buffer.readAngle();
        pitch = buffer.readAngle();
        headPitch = buffer.readAngle();
        velocity = new Velocity(buffer.readShort(), buffer.readShort(), buffer.readShort());
    }

}
