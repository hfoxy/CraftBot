package me.hfox.craftbot.protocol.play.client;

import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.play.client.data.ChatMode;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketClientPlayClientSettings implements ClientPacket {

    private String locale;
    private byte viewDistance;
    private ChatMode chatMode;
    private boolean colours;
    private byte skinParts;
    private Hand mainHand;

    public PacketClientPlayClientSettings(String locale, int viewDistance, ChatMode chatMode, boolean colours, int skinParts, Hand mainHand) {
        this.locale = locale;
        this.viewDistance = (byte) viewDistance;
        this.chatMode = chatMode;
        this.colours = colours;
        this.skinParts = (byte) skinParts;
        this.mainHand = mainHand;
    }

    public String getLocale() {
        return locale;
    }

    public byte getViewDistance() {
        return viewDistance;
    }

    public ChatMode getChatMode() {
        return chatMode;
    }

    public boolean isColours() {
        return colours;
    }

    public byte getSkinParts() {
        return skinParts;
    }

    public Hand getMainHand() {
        return mainHand;
    }

    @Override
    public void write(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        buffer.writeString(locale);
        buffer.writeByte(viewDistance);
        buffer.writeVarInt(chatMode.ordinal());
        buffer.writeBoolean(colours);
        buffer.writeByte(skinParts);
        buffer.writeVarInt(mainHand.ordinal());
    }

}
