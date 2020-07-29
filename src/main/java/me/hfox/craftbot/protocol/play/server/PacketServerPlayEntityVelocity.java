package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Velocity;

import java.io.IOException;

public class PacketServerPlayEntityVelocity implements ServerPacket {

    private int entityId;
    private Velocity velocity;

    public int getEntityId() {
        return entityId;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        velocity = new Velocity(buffer.readShort(), buffer.readShort(), buffer.readShort());
    }

}
