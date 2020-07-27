package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.hfox.craftbot.exception.BotCorruptedFrameException;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketSplitter.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        LOGGER.info("Received data!");

        ProtocolBuffer buffer = new ProtocolBuffer(byteBuf);

        // read the packet length
        // determine if the number of available bytes in the stream is < packet length
        // if less, reset and do nothing
        // if >= read all, mark and add ByteBuf of content to list

        // first <= 0 is end of a var int

        /*byteBuf.markReaderIndex();

        ByteBuf buf = Unpooled.buffer();
        ProtocolBuffer lengthBuffer = new ProtocolBuffer(buf);

        for (int i = 0; i < 3; i++) {
            if (!buffer.isReadable()) {
                buffer.resetReaderIndex();
                return;
            }

            byte read = buffer.readByte();
            LOGGER.info("Read {} for length ({})", read, Integer.toBinaryString(read));
            // 11111110000001

            if (read > 0) {
                // end of length
                buffer.resetReaderIndex();
                int length = buffer.readVarInt();
                LOGGER.debug("Received Packet with length of {}", length);

                if (buffer.readableBytes() >= length) {
                    list.add(buffer.readBytes(lengthBuffer).retain());
                    return;
                }

                buffer.resetReaderIndex();
                return;
            }
        }

        throw new BotCorruptedFrameException("length longer than 21 bits");*/

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
        super.exceptionCaught(ctx, cause);
        LOGGER.error("uh oh", cause);
    }

}
