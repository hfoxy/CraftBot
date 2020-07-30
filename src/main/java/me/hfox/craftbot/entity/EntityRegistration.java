package me.hfox.craftbot.entity;

import me.hfox.craftbot.entity.data.creation.EntityCreationData;
import me.hfox.craftbot.entity.data.creation.PlayerCreationData;
import me.hfox.craftbot.entity.data.creation.PlayerEntityType;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;

import java.util.HashMap;
import java.util.Map;

public class EntityRegistration {

    static final Map<Integer, EntityType<?, ?>> REGISTERED_ENTITIES = new HashMap<>();
    static final Map<String, EntityType<?, ?>> IDENTIFIER_REGISTERED_ENTITIES = new HashMap<>();

    public static Player createPlayer(PlayerCreationData data) {
        return (Player) createEntity(EntityType.PLAYER.getTypeId(), data);
    }

    public static Entity createEntity(int typeId, EntityCreationData data) {
        EntityType<?, ?> type = REGISTERED_ENTITIES.get(typeId);
        if (type == null) {
            throw new BotUnsupportedEntityException("Unknown Entity Type #" + typeId);
        }

        return type.createEntity(data);
    }

}
