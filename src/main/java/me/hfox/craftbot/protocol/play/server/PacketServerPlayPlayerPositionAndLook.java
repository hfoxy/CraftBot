package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class PacketServerPlayPlayerPositionAndLook implements ServerPacket {

    private double x;
    private boolean xRelative;

    private double y;
    private boolean yRelative;

    private double z;
    private boolean zRelative;

    private float yaw;
    private boolean yawRelative;

    private float pitch;
    private boolean pitchRelative;

    private int teleportId;

    public double getX() {
        return x;
    }

    public boolean isxRelative() {
        return xRelative;
    }

    public double getY() {
        return y;
    }

    public boolean isyRelative() {
        return yRelative;
    }

    public double getZ() {
        return z;
    }

    public boolean iszRelative() {
        return zRelative;
    }

    public float getYaw() {
        return yaw;
    }

    public boolean isYawRelative() {
        return yawRelative;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isPitchRelative() {
        return pitchRelative;
    }

    public int getTeleportId() {
        return teleportId;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        x = buffer.readDouble();
        y = buffer.readDouble();
        z = buffer.readDouble();
        yaw = buffer.readFloat();
        pitch = buffer.readFloat();

        byte flags = buffer.readByte();
        xRelative = hasFlag(flags, 0x01);
        yRelative = hasFlag(flags, 0x02);
        zRelative = hasFlag(flags, 0x04);
        yawRelative = hasFlag(flags, 0x08);
        pitchRelative = hasFlag(flags, 0x10);

        teleportId = buffer.readVarInt();
    }

}
