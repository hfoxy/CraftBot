package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayPlayerListHeaderFooter implements ServerPacket {

    private ChatComponent header;
    private ChatComponent footer;

    public ChatComponent getHeader() {
        return header;
    }

    public ChatComponent getFooter() {
        return footer;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        header = buffer.readChat();
        footer = buffer.readChat();
    }

}
