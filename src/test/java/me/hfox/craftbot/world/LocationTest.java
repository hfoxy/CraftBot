package me.hfox.craftbot.world;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    void getRelativeChunkPos() {
        Location location = new Location(-416, 0, 48);
        // expected values are x=0, y=0, z=0

        assertEquals(0, location.getChunkBlockX());
        assertEquals(0, location.getChunkBlockZ());

        location = new Location(-415, 0, 49);
        // expected values are x=1, y=0, z=1

        assertEquals(1, location.getChunkBlockX());
        assertEquals(1, location.getChunkBlockZ());

        location = new Location(-409, 0, 49);
        // expected values are x=7, y=0, z=1

        assertEquals(7, location.getChunkBlockX());
        assertEquals(1, location.getChunkBlockZ());
    }

}