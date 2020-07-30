package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import me.hfox.craftbot.connection.ServerConnection;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.JavaProtocol;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayKeepAlive;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProtocolHandlerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtocolHandlerTest.class);

    // @Test
    void writeRead() throws Exception {
        ServerConnection connection = new ServerConnection(null, null, new JavaProtocol());
        connection.getCompression().enable(256);

        PacketClientPlayKeepAlive packet = new PacketClientPlayKeepAlive(System.currentTimeMillis());

        PacketEncoder encoder = new PacketEncoder(connection);
        PacketPrepender prepender = new PacketPrepender(connection);
        PacketDeflator deflator = new PacketDeflator(connection);

        ByteBuf buffer = Unpooled.buffer();
        encoder.encode(null, packet, buffer);

        ByteBuf prepended = Unpooled.buffer();
        prepender.encode(null, buffer, prepended);

        ByteBuf deflated = Unpooled.buffer();
        deflator.encode(null, prepended, deflated);

        deflated.markReaderIndex();

        byte[] output = new byte[deflated.readableBytes()];
        deflated.readBytes(output);
        deflated.resetReaderIndex();

        LOGGER.info("Deflated output: {}", output);

        // packet sent away

        // packet read back
        PacketSplitter splitter = new PacketSplitter();
        PacketInflater inflater = new PacketInflater(connection);
        PacketDecoder decoder = new PacketDecoder(connection);

        List<Object> objects = new ArrayList<>();
        splitter.decode(null, deflated, objects);

        List<Object> inflated = new ArrayList<>();
        for (Object object : objects) {
            if (object instanceof ByteBuf) {
                ByteBuf data = (ByteBuf) object;
                data.markReaderIndex();

                byte[] content = new byte[data.readableBytes()];
                data.readBytes(content);
                data.resetReaderIndex();

                LOGGER.info("Splitter content: {}", content);
                inflater.decode(null, (ByteBuf) object, inflated);
            }
        }

        List<Object> decoded = new ArrayList<>();
        for (Object object : inflated) {
            if (object instanceof ByteBuf) {
                ByteBuf data = (ByteBuf) object;
                data.markReaderIndex();

                byte[] content = new byte[data.readableBytes()];
                data.readBytes(content);
                data.resetReaderIndex();

                LOGGER.info("Inflater content: {}", content);

                decoder.decode(null, (ByteBuf) object, decoded);
            }
        }
    }

}