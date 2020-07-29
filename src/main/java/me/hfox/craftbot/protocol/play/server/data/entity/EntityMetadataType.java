package me.hfox.craftbot.protocol.play.server.data.entity;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.entity.data.Pose;
import me.hfox.craftbot.entity.data.VillagerData;
import me.hfox.craftbot.entity.particle.Particle;
import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;
import me.hfox.craftbot.nbt.Tag;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.Direction;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.Rotation;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public enum EntityMetadataType {

    BYTE(0) {
        @Override
        public Byte read(ProtocolBuffer buffer) {
            return buffer.readByte();
        }
    },
    VAR_INT(1) {
        @Override
        public Integer read(ProtocolBuffer buffer) {
            return buffer.readVarInt();
        }
    },
    FLOAT(2) {
        @Override
        public Float read(ProtocolBuffer buffer) {
            return buffer.readFloat();
        }
    },
    STRING(3) {
        @Override
        public String read(ProtocolBuffer buffer) {
            return buffer.readString();
        }
    },
    CHAT(4) {
        @Override
        public ChatComponent read(ProtocolBuffer buffer) throws IOException {
            return buffer.readChat();
        }
    },
    CHAT_OPTIONAL(5) {
        @Override
        public Optional<ChatComponent> read(ProtocolBuffer buffer) throws IOException {
            return buffer.readChatOptional();
        }
    },
    SLOT(6) {
        @Override
        public SlotData read(ProtocolBuffer buffer) {
            return buffer.readSlot();
        }
    },
    BOOLEAN(7) {
        @Override
        public Boolean read(ProtocolBuffer buffer) {
            return buffer.readBoolean();
        }
    },
    ROTATION(8) {
        @Override
        public Rotation read(ProtocolBuffer buffer) {
            return buffer.readRotation();
        }
    },
    POSITION(9) {
        @Override
        public Location read(ProtocolBuffer buffer) {
            return buffer.readPosition();
        }
    },
    POSITION_OPTIONAL(10) {
        @Override
        public Optional<Location> read(ProtocolBuffer buffer) {
            return buffer.readPositionOptional();
        }
    },
    DIRECTION(11) {
        @Override
        public Direction read(ProtocolBuffer buffer) {
            return buffer.readDirection();
        }
    },
    UUID_OPTIONAL(12) {
        @Override
        public Optional<UUID> read(ProtocolBuffer buffer) {
            return buffer.readUuidOptional();
        }
    },
    BLOCK_ID_OPTIONAL(13) {
        @Override
        public Optional<Integer> read(ProtocolBuffer buffer) {
            return buffer.readBlockIdOptional();
        }
    },
    NBT(14) {
        @Override
        public Tag read(ProtocolBuffer buffer) throws IOException {
            return buffer.readTag();
        }
    },
    PARTICLE(15) {
        @Override
        public Particle read(ProtocolBuffer buffer) {
            return buffer.readParticle();
        }
    },
    VILLAGER_DATA(16) {
        @Override
        public VillagerData read(ProtocolBuffer buffer) {
            return buffer.readVillagerData();
        }
    },
    VAR_INT_OPTIONAL(17) {
        @Override
        public Optional<Integer> read(ProtocolBuffer buffer) {
            return buffer.readVarIntOptional();
        }
    },
    POSE(18) {
        @Override
        public Pose read(ProtocolBuffer buffer) {
            return buffer.readPose();
        }
    };

    private final int id;

    EntityMetadataType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract Object read(ProtocolBuffer buffer) throws IOException;

    public static EntityMetadataType findById(int id) {
        for (EntityMetadataType metadataType : values()) {
            if (metadataType.getId() == id) {
                return metadataType;
            }
        }

        throw new BotUnsupportedEntityException("Unknown metadata type: " + id);
    }

}
