package me.hfox.craftbot.protocol.play.server;

import io.netty.buffer.Unpooled;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayParticle implements ServerPacket {

    private int particleTypeId;
    private boolean longDistance;
    private double x;
    private double y;
    private double z;
    private float offsetX;
    private float offsetY;
    private float offsetZ;
    private float data;
    private int count;
    private ProtocolBuffer dataBuffer;

    public int getParticleTypeId() {
        return particleTypeId;
    }

    public boolean isLongDistance() {
        return longDistance;
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

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public float getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public ProtocolBuffer getDataBuffer() {
        return dataBuffer;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        particleTypeId = buffer.readInt();
        longDistance = buffer.readBoolean();
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        offsetX = buffer.readFloat();
        offsetY = buffer.readFloat();
        offsetZ = buffer.readFloat();
        data = buffer.readFloat();
        count = buffer.readInt();

        byte[] content = new byte[buffer.readableBytes()];
        buffer.readBytes(content);
        dataBuffer = new ProtocolBuffer(Unpooled.copiedBuffer(content));
    }

}
