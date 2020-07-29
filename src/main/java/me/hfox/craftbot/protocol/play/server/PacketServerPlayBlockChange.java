package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketServerPlayBlockChange implements ServerPacket {

    private Location position;
    private int blockId;

    public Location getPosition() {
        return position;
    }

    public int getBlockId() {
        return blockId;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        position = buffer.readPosition();
        blockId = buffer.readVarInt();
    }

}
