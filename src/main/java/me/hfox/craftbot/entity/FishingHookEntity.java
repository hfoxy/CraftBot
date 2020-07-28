package me.hfox.craftbot.entity;

import java.util.Optional;

public interface FishingHookEntity extends Entity {

    Optional<Integer> getHookedEntityId();

}
