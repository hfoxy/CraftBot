package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayHeldItemChange implements ServerPacket {

    private byte slot;

    public byte getSlot() {
        return slot;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        slot = buffer.readByte();
    }

}
