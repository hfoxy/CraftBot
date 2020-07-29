package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayDisconnect implements ServerPacket {

    private ChatComponent reason;

    public ChatComponent getReason() {
        return reason;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        reason = buffer.readChat();
    }

}
