package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.util.zip.Deflater;

public class PacketDeflator extends MessageToByteEncoder<ByteBuf> {

    private final Connection connection;

    public PacketDeflator(Connection connection) {
        this.connection = connection;
    }

    /**
     * Compresses the input buffer when compression is set and the packet is larger than the threshold
     *
     * @param channelHandlerContext The channel which this packet is being sent through
     * @param input                 The input buffer
     * @param output                The output buffer (may be compressed)
     *
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ByteBuf input, ByteBuf output) throws Exception {
        int length = input.readableBytes();
        if (connection.getCompression().isEnabled() && connection.getCompression().getThreshold() >= length) {
            ProtocolBuffer out = new ProtocolBuffer(output);
            out.writeVarInt(input.readableBytes());

            byte[] in = input.array();
            Deflater deflater = new Deflater();
            deflater.setInput(in);
            deflater.finish();

            byte[] bytes = new byte[length];
            int len = deflater.deflate(bytes);
            deflater.end();

            byte[] trim = new byte[len];
            System.arraycopy(bytes, 0, trim, 0, trim.length);

            output.writeBytes(trim);

            return;
        }

        output.writeBytes(input);
    }

}
