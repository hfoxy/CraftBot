package me.hfox.craftbot.protocol;

import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public interface ClientPacket extends Packet {

    /**
     * Writes the packet content to the buffer
     *
     * @param buffer the buffer to be written to
     */
    void write(ProtocolBuffer buffer);

}
