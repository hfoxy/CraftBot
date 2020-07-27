package me.hfox.craftbot.protocol;

import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.exception.BotProtocolException;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public interface Protocol {

    ProtocolState getState();

    void setState(ProtocolState state);

    /**
     * Writes a Packet to the buffer
     *
     * @param connection the server connection
     * @param buffer the buffer to write to
     * @param packet the packet to tbe written
     */
    void write(Connection connection, ProtocolBuffer buffer, ClientPacket packet) throws BotProtocolException;

    /**
     * Reads a Packet from the buffer
     *
     * @param connection the server connection
     * @param buffer the buffer to read from
     * @return the read packet
     */
    ServerPacket read(Connection connection, ProtocolBuffer buffer) throws BotProtocolException;

}
