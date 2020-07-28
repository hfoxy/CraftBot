package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayEntityHeadLook implements ServerPacket {

    private int entityId;
    private float yaw;

    public int getEntityId() {
        return entityId;
    }

    public float getYaw() {
        return yaw;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        yaw = buffer.readAngle();
    }

}
