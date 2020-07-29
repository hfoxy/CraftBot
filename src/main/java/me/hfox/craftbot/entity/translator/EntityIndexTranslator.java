package me.hfox.craftbot.entity.translator;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.protocol.play.server.data.entity.EntityMetadata;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public interface EntityIndexTranslator<E extends Entity> {

    void checkAndRead(Entity entity, EntityMetadata metadata) throws IOException;

    void read(E entity, EntityMetadata metadata) throws IOException;

}
