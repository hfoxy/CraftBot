package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayPluginMessage implements ServerPacket {

    private String channel;
    private byte[] data;

    public String getChannel() {
        return channel;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        channel = buffer.readString();
        data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
    }

}
