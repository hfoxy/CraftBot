package me.hfox.craftbot.entity;

import java.util.Optional;

public interface FishingHook extends Entity {

    Optional<Integer> getHookedEntityId();

}
