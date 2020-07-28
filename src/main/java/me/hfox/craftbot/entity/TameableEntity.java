package me.hfox.craftbot.entity;

import java.util.Optional;
import java.util.UUID;

public interface TameableEntity extends AnimalEntity {

    boolean isSitting();

    void setSitting(boolean sitting);

    boolean isAngry();

    void setAngry(boolean angry);

    boolean isTamed();

    void setTamed(boolean tamed);

    Optional<UUID> getOwner();

    void setOwner(UUID owner);

}
