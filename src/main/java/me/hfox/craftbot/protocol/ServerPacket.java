package me.hfox.craftbot.protocol;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public interface ServerPacket extends Packet {

    /**
     * Reads the packet content from the buffer
     *
     * @param buffer the buffer to be read from
     */
    void read(ProtocolBuffer buffer) throws IOException, BotProtocolException;

}
