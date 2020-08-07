package me.hfox.craftbot.world.impl;

import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.living.Player;
import me.hfox.craftbot.exception.world.BotChunkNotLoadedException;
import me.hfox.craftbot.world.Chunk;
import me.hfox.craftbot.world.Location;
import me.hfox.craftbot.world.World;
import me.hfox.craftbot.world.palette.BlockPalette;
import me.hfox.craftbot.world.palette.BlockStateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CraftWorld implements World {

    private final Client client;

    private final Map<Integer, Entity> entities;
    private final Object entityLock = new Object();
    private final Object playersLock = new Object();
    private final Set<Player> players;

    private final Object chunkLock = new Object();
    private final Map<Integer, Map<Integer, Chunk>> chunks;
    private final ChunkBuilder chunkBuilder;

    public CraftWorld(Client client) {
        this.client = client;
        this.entities = new HashMap<>();
        this.players = new HashSet<>();
        this.chunks = new HashMap<>();

        if (BlockPalette.getBiggestId() > Short.MAX_VALUE) {
            this.chunkBuilder = CraftChunkInt::new;
        } else {
            this.chunkBuilder = CraftChunkShort::new;
        }
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public Map<Integer, Entity> getEntities() {
        synchronized (entityLock) {
            return new HashMap<>(entities);
        }
    }

    @Override
    public Optional<Entity> findEntityById(int entityId) {
        return Optional.ofNullable(getEntities().get(entityId));
    }

    @Override
    public Set<Player> getPlayers() {
        synchronized (playersLock) {
            return new HashSet<>(players);
        }
    }

    @Override
    public <E extends Entity> E addEntity(E entity) {
        synchronized (entityLock) {
            entities.put(entity.getId(), entity);

            synchronized (playersLock) {
                if (entity instanceof Player) {
                    players.add((Player) entity);
                }
            }

            return entity;
        }
    }

    @Override
    public List<Entity> removeEntitiesById(int[] entityIds) {
        synchronized (entityLock) {
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

    @Override
    public Optional<Chunk> getChunk(int chunkX, int chunkZ) {
        synchronized (chunkLock) {
            Map<Integer, Chunk> xChunks = chunks.get(chunkX);
            if (xChunks == null) {
                return Optional.empty();
            }

            return Optional.ofNullable(xChunks.get(chunkZ));
        }
    }

    @Override
    public Optional<Chunk> getChunkByLocation(Location location) {
        return getChunk(location.getChunkX(), location.getChunkZ());
    }

    @Override
    public synchronized Chunk loadChunk(int chunkX, int chunkZ) {
        synchronized (chunkLock) {
            return chunks.computeIfAbsent(chunkX, (x) -> new ConcurrentHashMap<>()).computeIfAbsent(chunkZ, (z) -> chunkBuilder.build(chunkX, chunkZ));
        }
    }

    @Override
    public void unloadChunk(int chunkX, int chunkZ) {
        synchronized (chunkLock) {
            Map<Integer, Chunk> xChunks = chunks.get(chunkX);
            if (xChunks != null) {
                xChunks.remove(chunkZ);
            }
        }
    }

    private Chunk getChunk(Location location) {
        return getChunkByLocation(location).orElseThrow(
                () -> new BotChunkNotLoadedException("Chunk[x=" + location.getChunkX() + ", z=" + location.getChunkZ() + "] - Location[x=" + location.getX() + "/blockX=" + location.getBlockX() + ", z=" + location.getZ() + "/blockZ=" + location.getBlockZ() + "]")
        );
    }

    @Override
    public BlockStateDto getBlock(Location location) {
        return getChunk(location).getBlockAt(location);
    }

    @Override
    public void setBlock(Location location, BlockStateDto blockState) {
        getChunk(location).setBlockAt(location, blockState);
    }

}
