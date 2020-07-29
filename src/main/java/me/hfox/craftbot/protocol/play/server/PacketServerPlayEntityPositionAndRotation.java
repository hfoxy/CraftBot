package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketServerPlayEntityPositionAndRotation extends PacketServerPlayEntityPosition {

    private float yaw;
    private float pitch;

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    @Override
    protected void readYawPitch(ProtocolBuffer buffer) {
        yaw = buffer.readAngle();
        pitch = buffer.readAngle();
    }

}
