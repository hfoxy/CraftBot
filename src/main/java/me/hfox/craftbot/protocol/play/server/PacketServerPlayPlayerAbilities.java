package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class PacketServerPlayPlayerAbilities implements ServerPacket {

    private PlayerAbilities abilities;
    private float flyingSpeed;
    private float fieldOfViewModifier;

    public PlayerAbilities getAbilities() {
        return abilities;
    }

    public float getFlyingSpeed() {
        return flyingSpeed;
    }

    public float getFieldOfViewModifier() {
        return fieldOfViewModifier;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        byte flags = buffer.readByte();
        abilities = new PlayerAbilities(hasFlag(flags, 0x1), hasFlag(flags, 0x2), hasFlag(flags, 0x4), hasFlag(flags, 0x8));
        flyingSpeed = buffer.readFloat();
        fieldOfViewModifier = buffer.readFloat();
    }

    public static class PlayerAbilities {

        private final boolean invulnerable;
        private final boolean flying;
        private final boolean allowFlying;
        private final boolean instantBreak;

        public PlayerAbilities(boolean invulnerable, boolean flying, boolean allowFlying, boolean instantBreak) {
            this.invulnerable = invulnerable;
            this.flying = flying;
            this.allowFlying = allowFlying;
            this.instantBreak = instantBreak;
        }

        public boolean isInvulnerable() {
            return invulnerable;
        }

        public boolean isFlying() {
            return flying;
        }

        public boolean isAllowFlying() {
            return allowFlying;
        }

        public boolean isInstantBreak() {
            return instantBreak;
        }

    }

}
