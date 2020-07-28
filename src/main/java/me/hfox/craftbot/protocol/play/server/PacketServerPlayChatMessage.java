package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.chat.ChatPosition;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayChatMessage implements ServerPacket {

    private ChatComponent chat;
    private ChatPosition position;

    public ChatComponent getChat() {
        return chat;
    }

    public ChatPosition getPosition() {
        return position;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        chat = buffer.readChat();
        position = ChatPosition.findById(buffer.readByte());
    }

}
