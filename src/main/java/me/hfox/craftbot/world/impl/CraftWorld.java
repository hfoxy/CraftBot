package me.hfox.craftbot.world.impl;

import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CraftWorld implements World {

    private static final Logger LOGGER = LoggerFactory.getLogger(CraftWorld.class);

    private final Client client;

    private final Map<Integer, Entity> entities;
    private final Object playersLock = new Object();
    private final Set<Player> players;

    public CraftWorld(Client client) {
        this.client = client;
        this.entities = new ConcurrentHashMap<>();
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
    public Optional<Entity> findEntityById(int entityId) {
        return Optional.ofNullable(entities.get(entityId));
    }

    @Override
    public Set<Player> getPlayers() {
        synchronized (playersLock) {
            return new HashSet<>(players);
        }
    }

    @Override
    public <E extends Entity> E addEntity(E entity) {
        entities.put(entity.getId(), entity);

        synchronized (playersLock) {
            if (entity instanceof Player) {
                players.add((Player) entity);
            }
        }

        return entity;
    }

    @Override
    public List<Entity> removeEntitiesById(int[] entityIds) {
        List<Entity> list = new ArrayList<>();
        for (int entityId : entityIds) {
            Entity removed = entities.remove(entityId);
            synchronized (playersLock) {
                if (removed instanceof Player) {
                    players.remove(removed);
                    // TODO: trigger some form of player disconnect event?
                }
            }

            if (removed != null) {
                list.add(removed);
            }
        }

        return list;
    }

}
