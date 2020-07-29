package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;
import java.util.Optional;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class PacketServerPlayStopSound implements ServerPacket {

    private Integer sourceId;
    private String soundName;

    public Optional<Integer> getSourceId() {
        return Optional.ofNullable(sourceId);
    }

    public Optional<String> getSoundName() {
        return Optional.ofNullable(soundName);
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        byte flags = buffer.readByte();
        if (hasFlag(flags, 0x01)) {
            sourceId = buffer.readVarInt();
        }

        if (hasFlag(flags, 0x02)) {
            soundName = buffer.readString();
        }
    }

}
