package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.entity.properties.EntityProperty;
import me.hfox.craftbot.entity.properties.EntityPropertyModifier;
import me.hfox.craftbot.entity.properties.EntityPropertyModifierOperation;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketServerPlayEntityProperties implements ServerPacket {

    private int entityId;
    private EntityProperty[] properties;

    public int getEntityId() {
        return entityId;
    }

    public EntityProperty[] getProperties() {
        return properties;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        entityId = buffer.readVarInt();

        properties = new EntityProperty[buffer.readInt()];
        for (int i = 0; i < properties.length; i++) {
            String key = buffer.readString();
            double value = buffer.readDouble();
            List<EntityPropertyModifier> modifiers = new ArrayList<>();

            int modifierCount = buffer.readVarInt();
            for (int j = 0; j < modifierCount; j++) {
                UUID uuid = buffer.readUuid();
                double valueModifier = buffer.readDouble();
                EntityPropertyModifierOperation operation = EntityPropertyModifierOperation.values()[buffer.readByte()];

                modifiers.add(new EntityPropertyModifier(uuid, valueModifier, operation));
            }

            properties[i] = new EntityProperty(key, value, modifiers);
        }
    }

}
