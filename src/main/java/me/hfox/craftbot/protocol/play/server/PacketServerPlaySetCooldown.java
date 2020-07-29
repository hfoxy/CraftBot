package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlaySetCooldown implements ServerPacket {

    private int itemId;
    private int cooldownTicks;

    public int getItemId() {
        return itemId;
    }

    public int getCooldownTicks() {
        return cooldownTicks;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        itemId = buffer.readVarInt();
        cooldownTicks = buffer.readVarInt();
    }

}
