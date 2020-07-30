package me.hfox.craftbot.entity;

import com.google.common.base.Preconditions;
import me.hfox.craftbot.entity.data.creation.EntityCreationData;
import me.hfox.craftbot.entity.data.creation.PlayerCreationData;
import me.hfox.craftbot.entity.data.creation.PlayerEntityType;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.entity.translator.EntityIndexTranslator;
import me.hfox.craftbot.exception.entity.BotUnsupportedEntityException;
import me.hfox.craftbot.world.World;

import java.lang.reflect.Constructor;
import java.util.UUID;

import static me.hfox.craftbot.entity.EntityRegistration.IDENTIFIER_REGISTERED_ENTITIES;
import static me.hfox.craftbot.entity.EntityRegistration.REGISTERED_ENTITIES;

public class EntityType<E extends Entity, D extends EntityCreationData> {

    private final Class<? extends E> type;
    private final int typeId;
    private final String name;
    private final String identifier;
    private final Class<? extends D> dataType;
    private final EntityIndexTranslator<?> translator;

    public EntityType(Class<? extends E> type, int typeId, String name, String identifier, Class<? extends D> dataType, EntityIndexTranslator<?> translator) {
        if (REGISTERED_ENTITIES.containsKey(typeId)) {
            throw new BotUnsupportedEntityException("Entity already registered with ID #" + typeId);
        }

        if (IDENTIFIER_REGISTERED_ENTITIES.containsKey(identifier)) {
            throw new BotUnsupportedEntityException("Entity already registered with Identifier #" + identifier);
        }

        REGISTERED_ENTITIES.put(typeId, this);
        IDENTIFIER_REGISTERED_ENTITIES.put(identifier, this);

        this.type = type;
        this.typeId = typeId;
        this.name = name;
        this.identifier = identifier;
        this.dataType = dataType;
        this.translator = translator;
    }

    public Class<? extends E> getType() {
        return type;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public EntityIndexTranslator<?> getTranslator() {
        return translator;
    }

    @SuppressWarnings("unchecked")
    public E createEntity(EntityCreationData data) {
        Preconditions.checkNotNull(data, "data cannot be null");

        if (!dataType.isAssignableFrom(data.getClass())) {
            throw new BotUnsupportedEntityException("Wrong data type (" + data.getClass().getSimpleName()
                    + ") for Entity (" + type.getSimpleName() + ") requires " + dataType.getSimpleName());
        }

        D d = (D) data;
        return createEntityMappedData(d);
    }

    protected E createEntityMappedData(D data) {
        try {
            Constructor<? extends E> constructor = type.getDeclaredConstructor(World.class, int.class, UUID.class, EntityType.class);
            return constructor.newInstance(data.getWorld(), data.getEntityId(), data.getUuid(), this);
        } catch (Exception ex) {
            throw new BotUnsupportedEntityException("Unable to construct Entity", ex);
        }
    }

}
