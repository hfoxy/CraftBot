package me.hfox.craftbot.handling;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.EntityRegistration;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class TickHandler implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickHandler.class);

    private final ClientHandler clientHandler;
    private final AtomicInteger tickCounter;

    private Set<Integer> savedMissing;

    public TickHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        this.tickCounter = new AtomicInteger();
    }

    @Override
    public void run() {
        try {
            int tick = tickCounter.getAndIncrement();

            int currentSize = EntityRegistration.MISSING_ENTITIES.size();
            if (savedMissing != null && savedMissing.size() != currentSize) {
                LOGGER.info("Missing entities ({}): {} - found {} new", currentSize, EntityRegistration.MISSING_ENTITIES, (currentSize - savedMissing.size()));
            } else if (savedMissing != null && tick > 0 && tick % 100 == 0) {
                LOGGER.info("Missing entities ({}): {}", currentSize, EntityRegistration.MISSING_ENTITIES);
                clientHandler.getClient().getConnection().writePacket(new PacketClientPlayChatMessage("Missing " + currentSize + " entities: " + savedMissing));
            }

            savedMissing = new HashSet<>(EntityRegistration.MISSING_ENTITIES);

            Collection<Entity> entities = clientHandler.getWorldHandler().getWorld().getEntities().values();
            // LOGGER.info("Aware of {} entities: /{/}", entities.size()); //, entities);
        } catch (Throwable ex) {
            LOGGER.error("BROKEN", ex);
        }
    }

}
