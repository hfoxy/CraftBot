package me.hfox.craftbot.world.impl;

import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CraftWorld implements World {

    private static final Logger LOGGER = LoggerFactory.getLogger(CraftWorld.class);

    private final Client client;

    private final Map<Integer, Entity> entities;
    private final Set<Player> players;

    public CraftWorld(Client client) {
        this.client = client;
        this.entities = new HashMap<>();
        this.players = new HashSet<>();
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public Map<Integer, Entity> getEntities() {
        return new HashMap<>(entities);
    }

    @Override
    public Set<Player> getPlayers() {
        return new HashSet<>(players);
    }

    @Override
    public void addEntity(Entity entity) {
        LOGGER.info("Adding {}", entity.getClass().getSimpleName());

        entities.put(entity.getId(), entity);

        if (entity instanceof Player) {
            players.add((Player) entity);
        }
    }

}
