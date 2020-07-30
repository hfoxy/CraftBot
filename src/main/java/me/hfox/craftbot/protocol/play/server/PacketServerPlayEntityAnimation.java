package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityAnimation;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayEntityAnimation implements ServerPacket {

    private int entityId;
    private EntityAnimation animation;

    public int getEntityId() {
        return entityId;
    }

    public EntityAnimation getAnimation() {
        return animation;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        animation = EntityAnimation.values()[buffer.readUnsignedByte()];
    }

}
