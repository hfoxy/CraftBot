package me.hfox.craftbot.world;

import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.living.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface World {

    Client getClient();

    Map<Integer, Entity> getEntities();

    Optional<Entity> findEntityById(int entityId);

    Set<Player> getPlayers();

    <E extends Entity> E addEntity(E entity);

    List<Entity> removeEntitiesById(int[] entityIds);

}
