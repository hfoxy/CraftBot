package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.inventory.ItemSlot;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayEntityEquipment implements ServerPacket {

    private int entityId;
    private ItemSlot slot;
    private SlotData data;

    public int getEntityId() {
        return entityId;
    }

    public ItemSlot getSlot() {
        return slot;
    }

    public SlotData getData() {
        return data;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        slot = ItemSlot.values()[buffer.readVarInt()];
        data = buffer.readSlot();
    }

}
