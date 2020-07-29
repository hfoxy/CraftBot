package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlaySetPassengers implements ServerPacket {

    private int entityId;
    private int[] passengerIds;

    public int getEntityId() {
        return entityId;
    }

    public int[] getPassengerIds() {
        return passengerIds;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        passengerIds = new int[buffer.readVarInt()];
        for (int i = 0; i < passengerIds.length; i++) {
            passengerIds[i] = buffer.readVarInt();
        }
    }

}
