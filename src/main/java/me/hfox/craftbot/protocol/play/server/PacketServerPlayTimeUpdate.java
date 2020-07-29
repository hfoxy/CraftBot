package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayTimeUpdate implements ServerPacket {

    private long worldAge;
    private long timeOfDay;

    public long getWorldAge() {
        return worldAge;
    }

    public long getTimeOfDay() {
        return timeOfDay;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        worldAge = buffer.readLong();
        timeOfDay = buffer.readLong();
    }

}
