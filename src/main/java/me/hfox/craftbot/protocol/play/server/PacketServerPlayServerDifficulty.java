package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Difficulty;

import java.io.IOException;

public class PacketServerPlayServerDifficulty implements ServerPacket {

    private Difficulty difficulty;
    private boolean difficultyLocked;

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean isDifficultyLocked() {
        return difficultyLocked;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        difficulty = Difficulty.findById(buffer.readUnsignedByte());
        difficultyLocked = buffer.readBoolean();
    }

}
