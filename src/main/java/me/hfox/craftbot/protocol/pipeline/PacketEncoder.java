package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.hfox.craftbot.protocol.stream.ProtocolBuffer.getBuffer;

public class PacketEncoder extends MessageToByteEncoder<ClientPacket> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketEncoder.class);

    private final Connection connection;

    public PacketEncoder(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ClientPacket clientPacket, ByteBuf byteBuf) throws Exception {
        ProtocolBuffer buffer = getBuffer(byteBuf);

        try {
            connection.getProtocol().write(connection, buffer, clientPacket);
        } catch (Exception ex) {
            LOGGER.error("Error writing Packet", ex);
            connection.disconnect();
        }
    }

}
