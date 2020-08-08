package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketServerPlayBlockAction implements ServerPacket {

    private Location location;
    private int actionId;
    private int actionParam;
    private int blockId;

    public Location getLocation() {
        return location;
    }

    public int getActionId() {
        return actionId;
    }

    public int getActionParam() {
        return actionParam;
    }

    public int getBlockId() {
        return blockId;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        location = buffer.readPosition();
        actionId = buffer.readUnsignedByte();
        actionParam = buffer.readUnsignedByte();
        blockId = buffer.readVarInt();
    }

}
