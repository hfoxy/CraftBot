package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketClientPlayPlayerPositionAndRotation implements ClientPacket {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean grounded;

    public PacketClientPlayPlayerPositionAndRotation(double x, double y, double z, float yaw, float pitch, boolean grounded) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.grounded = grounded;
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
    public void write(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        buffer.writeDouble(x);
        buffer.writeDouble(y);
        buffer.writeDouble(z);
        buffer.writeFloat(yaw);
        buffer.writeFloat(pitch);
        buffer.writeBoolean(grounded);
    }

}
