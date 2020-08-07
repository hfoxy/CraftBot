package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class PacketServerPlayEntityEffect implements ServerPacket {

    private int entityId;
    private byte effectId;
    private byte amplifier;
    private int duration;
    private boolean ambient;
    private boolean particles;
    private boolean icon;

    public int getEntityId() {
        return entityId;
    }

    public byte getEffectId() {
        return effectId;
    }

    public byte getAmplifier() {
        return amplifier;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public boolean isParticles() {
        return particles;
    }

    public boolean isIcon() {
        return icon;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();
        effectId = buffer.readByte();
        amplifier = buffer.readByte();
        duration = buffer.readVarInt();

        byte flags = buffer.readByte();
        ambient = hasFlag(flags, 0x01);
        particles = hasFlag(flags, 0x02);
        icon = hasFlag(flags, 0x04);
    }

}
