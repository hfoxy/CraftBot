package me.hfox.craftbot.entity.living.mob.animal.tameable;

import me.hfox.craftbot.entity.living.mob.animal.Animal;

import java.util.Optional;
import java.util.UUID;

public interface Tameable extends Animal {

    boolean isSitting();

    void setSitting(boolean sitting);

    boolean isAngry();

    void setAngry(boolean angry);

    boolean isTamed();

    void setTamed(boolean tamed);

    Optional<UUID> getOwner();

    void setOwner(UUID owner);

}
