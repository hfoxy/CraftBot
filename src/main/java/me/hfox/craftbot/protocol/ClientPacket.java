package me.hfox.craftbot.protocol;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public interface ClientPacket extends Packet {

    /**
     * Writes the packet content to the buffer
     *
     * @param buffer the buffer to be written to
     */
    void write(ProtocolBuffer buffer) throws IOException, BotProtocolException;

}
