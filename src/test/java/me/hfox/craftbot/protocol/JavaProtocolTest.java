package me.hfox.craftbot.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.hfox.craftbot.connection.ServerConnection;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class JavaProtocolTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaProtocolTest.class);

    private static final int WARMUP = 100;
    private static final int COUNT = 10000 + WARMUP;

    private static final AtomicInteger counter = new AtomicInteger();
    private static final AtomicLong timer = new AtomicLong();

    private static final JavaProtocol protocol;
    private static final byte[] data;

    static {
        JavaProtocol ptcl;
        byte[] content;

        try {
            ptcl = new JavaProtocol();
            ptcl.setState(ProtocolState.PLAY);

            InputStream stream = JavaProtocolTest.class.getResourceAsStream("/chunk-file-44");
            content = new byte[stream.available()];
            int read = stream.read(content);
            if (read != content.length) {
                throw new IOException("Not enough bytes read");
            }
        } catch (Exception ex) {
            LOGGER.error("Unable to setup", ex);
            ptcl = null;
            content = null;
        }

        protocol = ptcl;
        data = content;
    }

    @RepeatedTest(COUNT)
    void spamChunkRead() throws BotProtocolException, IOException {
        if (protocol == null) {
            throw new UnsupportedOperationException("No protocol");
        }

        ProtocolBuffer buffer = new ProtocolBuffer(Unpooled.copiedBuffer(data));
        long start = System.nanoTime();
        protocol.read(null, buffer);
        long end = System.nanoTime();

        int count = counter.incrementAndGet();
        if (count > WARMUP) {
            timer.addAndGet(end - start);
        }

        if (count == COUNT) {
            int actions = COUNT - WARMUP;
            LOGGER.info("Took {}ns to complete {} actions", timer.get(), actions);
            LOGGER.info("Average of {}ns per action", timer.get() / actions);
        }
    }

}