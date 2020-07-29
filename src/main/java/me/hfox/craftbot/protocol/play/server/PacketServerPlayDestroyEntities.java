package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayDestroyEntities implements ServerPacket {

    private int[] entityIds;

    public int[] getEntityIds() {
        return entityIds;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityIds = new int[buffer.readVarInt()];
        for (int i = 0; i < entityIds.length; i++) {
            entityIds[i] = buffer.readVarInt();
        }
    }

}
