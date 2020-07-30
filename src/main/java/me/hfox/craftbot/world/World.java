package me.hfox.craftbot.world;

import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.entity.living.Player;

import java.util.Map;
import java.util.Set;

public interface World {

    Client getClient();

    Map<Integer, Entity> getEntities();

    Set<Player> getPlayers();

    void addEntity(Entity entity);

}
