package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {

    private final Connection connection;

    public PacketPrepender(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf input, ByteBuf byteBuf) throws Exception {
        int length = input.readableBytes();

        ProtocolBuffer buffer = new ProtocolBuffer(byteBuf);
        if (!connection.getCompression().isEnabled()) {
            buffer.writeVarInt(length);
        }

        buffer.writeBytes(input);
    }

}
