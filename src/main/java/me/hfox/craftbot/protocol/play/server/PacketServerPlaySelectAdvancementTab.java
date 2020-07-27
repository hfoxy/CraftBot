package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;
import java.util.Optional;

public class PacketServerPlaySelectAdvancementTab implements ServerPacket {

    private Optional<String> identifier;

    public Optional<String> getIdentifier() {
        return identifier;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        if (buffer.readBoolean()) {
            identifier = Optional.of(buffer.readString());
        } else {
            identifier = Optional.empty();
        }
    }

}
