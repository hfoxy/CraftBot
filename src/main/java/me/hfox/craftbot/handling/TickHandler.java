package me.hfox.craftbot.handling;

import me.hfox.craftbot.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

public class TickHandler implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TickHandler.class);

    private final ClientHandler clientHandler;

    public TickHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override
    public void run() {
        Collection<Entity> entities = clientHandler.getWorldHandler().getWorld().getEntities().values();
        LOGGER.info("Aware of {} entities: {}", entities.size(), entities);
    }

}
