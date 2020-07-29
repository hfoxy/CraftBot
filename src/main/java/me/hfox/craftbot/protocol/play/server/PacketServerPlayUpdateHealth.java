package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayUpdateHealth implements ServerPacket {

    private float health;
    private int food;
    private float saturation;

    public float getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public float getSaturation() {
        return saturation;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        health = buffer.readFloat();
        food = buffer.readVarInt();
        saturation = buffer.readFloat();
    }

}
