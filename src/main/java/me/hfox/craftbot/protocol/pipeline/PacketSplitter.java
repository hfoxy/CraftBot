package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.hfox.craftbot.exception.protocol.BotCorruptedFrameException;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketSplitter.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        ProtocolBuffer buffer = new ProtocolBuffer(byteBuf);

        buffer.markReaderIndex();
        if (buffer.readableBytes() < 3) {
            return;
        }

        byte[] length = new byte[3];

        for (int i = 0; i < length.length; ++i) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return;
            }

            length[i] = buffer.readByte();

            if (length[i] > 0) {
                ProtocolBuffer lbf = new ProtocolBuffer(byteBuf);
                buffer.resetReaderIndex();
                int size = lbf.readVarInt();
                LOGGER.debug("Read size as {}", size);

                if (buffer.readableBytes() >= size) {
                    LOGGER.debug("Adding packet of {} bytes to list", size);
                    list.add(buffer.readBytes(size));
                    return;
                }

                LOGGER.debug("Not enough data on stream, missing {} of {} bytes", (size - buffer.readableBytes()), size);
                buffer.resetReaderIndex();
                return;
            }
        }

        throw new BotCorruptedFrameException("length wider than 21-bit");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof SocketException) {
            // we have been disconnected :(
            return;
        }

        LOGGER.error("Unexpected error occurred!", cause);
    }

}
