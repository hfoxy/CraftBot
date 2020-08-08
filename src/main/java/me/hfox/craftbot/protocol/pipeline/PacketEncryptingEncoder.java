package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.hfox.craftbot.utils.CryptoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;

public class PacketEncryptingEncoder extends MessageToByteEncoder<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketEncryptingEncoder.class);

    private final Cipher cipher;

    public PacketEncryptingEncoder(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override
    protected synchronized void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        CryptoUtils.cipher(cipher, msg, out);
    }

}
