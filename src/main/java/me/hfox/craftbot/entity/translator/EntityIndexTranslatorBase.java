package me.hfox.craftbot.entity.translator;

import com.google.common.base.Preconditions;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public abstract class EntityIndexTranslatorBase<E extends Entity> implements EntityIndexTranslator<E> {

    private final Class<E> entityType;

    public EntityIndexTranslatorBase(Class<E> entityType) {
        this.entityType = entityType;
    }

    @Override
    @SuppressWarnings("unchecked") // it is checked
    public void checkAndRead(Entity entity, EntityMetadata metadata) throws IOException {
        Preconditions.checkNotNull(entity, "entity cannot be null");
        Preconditions.checkNotNull(metadata, "buffer cannot be null");

        if (!entityType.isAssignableFrom(entity.getClass())) {
            return;
        }

        read((E) entity, metadata);
    }

}
