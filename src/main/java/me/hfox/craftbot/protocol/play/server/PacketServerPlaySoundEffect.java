package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.world.SoundCategory;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlaySoundEffect implements ServerPacket {

    private int soundId;
    private SoundCategory soundCategory;
    private double x;
    private double y;
    private double z;
    private float volume;
    private float pitch;

    public int getSoundId() {
        return soundId;
    }

    public SoundCategory getSoundCategory() {
        return soundCategory;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        soundId = buffer.readVarInt();
        soundCategory = SoundCategory.values()[buffer.readVarInt()];
        x = (double) buffer.readInt() / 8;
        y = (double) buffer.readInt() / 8;
        z = (double) buffer.readInt() / 8;
        volume = buffer.readFloat();
        pitch = buffer.readFloat();
    }

}
