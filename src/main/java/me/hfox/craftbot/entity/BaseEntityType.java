package me.hfox.craftbot.entity;

import me.hfox.craftbot.entity.data.creation.EntityCreationData;

public class BaseEntityType<E extends Entity> extends EntityType<E, EntityCreationData> {

    public BaseEntityType(Class<? extends E> type, int typeId, String identifier) {
        super(type, typeId, identifier, EntityCreationData.class);
    }

}
