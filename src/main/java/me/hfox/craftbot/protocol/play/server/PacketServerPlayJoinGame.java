package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.player.Gamemode;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Dimension;
import me.hfox.craftbot.world.LevelType;

import java.io.IOException;

public class PacketServerPlayJoinGame implements ServerPacket {

    private int entityId;
    private Gamemode gamemode;
    private boolean hardcore;
    private Dimension dimension;
    private long seed;
    private short maxPlayers;
    private LevelType levelType;
    private int viewDistance;
    private boolean reducedDebug;
    private boolean respawnScreen;

    public int getEntityId() {
        return entityId;
    }

    public Gamemode getGamemode() {
        return gamemode;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public long getSeed() {
        return seed;
    }

    public short getMaxPlayers() {
        return maxPlayers;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public boolean isReducedDebug() {
        return reducedDebug;
    }

    public boolean isRespawnScreen() {
        return respawnScreen;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readInt();

        short gamemodeId = buffer.readUnsignedByte();
        hardcore = (gamemodeId & 0x8) == 0x8;
        gamemodeId = (short) (gamemodeId & 0xF);

        gamemode = Gamemode.findById(gamemodeId);
        dimension = Dimension.findById(buffer.readInt());
        seed = buffer.readLong();
        maxPlayers = buffer.readUnsignedByte();
        levelType = LevelType.findByName(buffer.readString());
        viewDistance = buffer.readVarInt();
        reducedDebug = buffer.readBoolean();
        respawnScreen = buffer.readBoolean();
    }

}
