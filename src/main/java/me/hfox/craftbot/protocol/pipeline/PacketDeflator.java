package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;

import static me.hfox.craftbot.protocol.stream.ProtocolBuffer.getBuffer;

public class PacketDeflator extends MessageToByteEncoder<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketDeflator.class);

    private final Connection connection;

    public PacketDeflator(Connection connection) {
        this.connection = connection;
    }

    /**
     * Compresses the input buffer when compression is set and the packet is larger than the threshold
     *
     * @param channelHandlerContext The channel which this packet is being sent through
     * @param inBuf                 The input buffer
     * @param outBuf                The output buffer (may be compressed)
     *
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf inBuf, ByteBuf outBuf) throws Exception {
        try {
            ProtocolBuffer input = getBuffer(inBuf);
            ProtocolBuffer output = getBuffer(outBuf);

            int length = input.readableBytes();
            if (connection.getCompression().isEnabled()) {
                int uncompressedContentLength = input.readableBytes();
                byte[] content = new byte[uncompressedContentLength];
                input.readBytes(content);

                if (length >= connection.getCompression().getThreshold()) {
                    Deflater deflater = new Deflater();
                    deflater.setInput(content);
                    deflater.finish();

                    byte[] bytes = new byte[length];
                    int len = deflater.deflate(bytes);
                    deflater.end();

                    byte[] trim = new byte[len];
                    System.arraycopy(bytes, 0, trim, 0, trim.length);
                    content = trim;
                } else {
                    uncompressedContentLength = 0;
                }

                ProtocolBuffer buffer = new ProtocolBuffer(Unpooled.buffer());
                buffer.writeVarInt(uncompressedContentLength);
                buffer.writeBytes(content);

                output.writeVarInt(buffer.writerIndex());
                output.writeBytes(buffer);
                return;
            }

            output.writeBytes(input);
        } catch (Exception ex) {

        }
    }

}
