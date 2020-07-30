package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Velocity;

import java.io.IOException;
import java.util.UUID;

public class PacketServerPlaySpawnLivingEntity extends PacketServerPlaySpawnEntity {

    private float headPitch;

    public float getHeadPitch() {
        return headPitch;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        readInfoAndXYZ(buffer);
        yaw = buffer.readAngle();
        pitch = buffer.readAngle();
        headPitch = buffer.readAngle();
        data = -1;
        velocity = new Velocity(buffer.readShort(), buffer.readShort(), buffer.readShort());
    }

}
