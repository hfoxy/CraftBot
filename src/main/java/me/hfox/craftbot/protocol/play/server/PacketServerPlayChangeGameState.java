package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.player.PlayerGameState;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.utils.ToStringBuilder;

import java.io.IOException;

public class PacketServerPlayChangeGameState implements ServerPacket {

    private PlayerGameState reason;
    private float value;

    public PlayerGameState getReason() {
        return reason;
    }

    public float getValue() {
        return value;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        reason = PlayerGameState.values()[buffer.readUnsignedByte()];
        value = buffer.readFloat();
    }

    @Override
    public String toString() {
        return ToStringBuilder.build(this).deepArray(true).parents(true).reflective(true).simpleName(true).toString();
    }

}
