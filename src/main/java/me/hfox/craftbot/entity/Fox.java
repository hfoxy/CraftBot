package me.hfox.craftbot.entity;

import java.util.Optional;
import java.util.UUID;

public interface Fox extends Animal {

    FoxType getType();

    void setType(FoxType foxType);

    boolean isSitting();

    void setSitting(boolean sitting);

    boolean isCrouching();

    void setCrouching(boolean crouching);

    boolean isSleeping();

    void setSleeping(boolean sleeping);

    Optional<UUID> getFirstUuid();

    void setFirstUuid(UUID uuid);

    Optional<UUID> getSecondUuid();

    void setSecondUuid(UUID uuid);

}
