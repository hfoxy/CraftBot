package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlaySetExperience implements ServerPacket {

    private float bar;
    private int level;
    private int totalExp;

    public float getBar() {
        return bar;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalExp() {
        return totalExp;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        bar = buffer.readFloat();
        level = buffer.readVarInt();
        totalExp = buffer.readVarInt();
    }

}
