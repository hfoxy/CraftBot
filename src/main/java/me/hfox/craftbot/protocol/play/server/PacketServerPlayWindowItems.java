package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayWindowItems implements ServerPacket {

    private short windowId;
    private SlotData[] slots;

    public int getWindowId() {
        return windowId;
    }

    public SlotData[] getSlots() {
        return slots;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        windowId = buffer.readUnsignedByte();
        slots = new SlotData[buffer.readShort()];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = buffer.readSlot();
        }
    }

}
