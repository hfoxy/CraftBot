package me.hfox.craftbot.entity.translator;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class HierarchyEntityIndexTranslatorBase<E extends Entity> extends EntityIndexTranslatorBase<E> {

    private final List<EntityIndexTranslator<?>> parents;

    public HierarchyEntityIndexTranslatorBase(Class<E> entityType, EntityIndexTranslator<?>... parents) {
        super(entityType);
        this.parents = Arrays.asList(parents);
    }

    @Override
    public void checkAndRead(Entity entity, EntityMetadata metadata) throws IOException {
        for (EntityIndexTranslator<?> parent : parents) {
            parent.checkAndRead(entity, metadata);
        }

        super.checkAndRead(entity, metadata);
    }

}
