package me.hfox.craftbot.protocol.login.server;

import me.hfox.craftbot.Bot;
import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerLoginDisconnect implements ServerPacket {

    private String reasonString;
    private ChatComponent reason;

    public String getReasonString() {
        return reasonString;
    }

    public ChatComponent getReason() {
        return reason;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException {
        reasonString = buffer.readString();
        reason = Bot.getBot().getMapper().readValue(reasonString, ChatComponent.class);
    }

}
