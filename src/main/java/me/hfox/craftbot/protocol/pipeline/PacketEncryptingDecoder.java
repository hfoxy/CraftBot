package me.hfox.craftbot.protocol.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.hfox.craftbot.utils.CryptoUtils;

import javax.crypto.Cipher;
import java.util.List;

public class PacketEncryptingDecoder extends MessageToMessageDecoder<ByteBuf> {

    private final Cipher cipher;

    public PacketEncryptingDecoder(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add(CryptoUtils.decipher(cipher, msg));
    }

}
