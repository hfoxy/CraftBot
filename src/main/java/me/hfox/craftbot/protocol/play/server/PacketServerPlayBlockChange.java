package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.palette.BlockStateDto;

import java.io.IOException;

public class PacketServerPlayBlockChange implements ServerPacket {

    private Location position;
    private BlockStateDto blockState;

    public Location getPosition() {
        return position;
    }

    public BlockStateDto getBlockState() {
        return blockState;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        position = buffer.readPosition();
        blockState = buffer.readBlockState();
    }

}
