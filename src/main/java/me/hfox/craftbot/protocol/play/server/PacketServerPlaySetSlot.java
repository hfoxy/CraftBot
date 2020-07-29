package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlaySetSlot implements ServerPacket {

    private byte windowId;
    private short slot;
    private SlotData data;

    public byte getWindowId() {
        return windowId;
    }

    public short getSlot() {
        return slot;
    }

    public SlotData getData() {
        return data;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        windowId = buffer.readByte();
        slot = buffer.readShort();
        data = buffer.readSlot();
    }

}
