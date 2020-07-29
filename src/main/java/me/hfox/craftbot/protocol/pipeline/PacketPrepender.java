package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf input, ByteBuf byteBuf) throws Exception {
        int length = input.readableBytes();

        ProtocolBuffer buffer = new ProtocolBuffer(byteBuf);
        buffer.writeVarInt(length);
        buffer.writeBytes(input);
    }

}
