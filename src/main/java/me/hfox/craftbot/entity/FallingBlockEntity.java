package me.hfox.craftbot.entity;

import me.hfox.craftbot.world.Location;

public interface FallingBlockEntity extends Entity {

    Location getSpawnPosition();

    void setSpawnPosition(Location spawnPosition);

}
