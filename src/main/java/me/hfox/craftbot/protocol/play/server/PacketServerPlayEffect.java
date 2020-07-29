package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Location;

import java.io.IOException;

public class PacketServerPlayEffect implements ServerPacket {

    private int effectId;
    private Location position;
    private int data;
    private boolean disableRelativeVolume;

    public int getEffectId() {
        return effectId;
    }

    public Location getPosition() {
        return position;
    }

    public int getData() {
        return data;
    }

    public boolean isDisableRelativeVolume() {
        return disableRelativeVolume;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        effectId = buffer.readInt();
        position = buffer.readPosition();
        data = buffer.readInt();
        disableRelativeVolume = buffer.readBoolean();
    }

}
