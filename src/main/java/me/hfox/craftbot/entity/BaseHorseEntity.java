package me.hfox.craftbot.entity;

import java.util.Optional;
import java.util.UUID;

public interface BaseHorseEntity extends AnimalEntity {

    boolean isTame();

    void setTame(boolean tame);

    boolean isSaddled();

    void setSaddled(boolean saddled);

    boolean hasBred();

    void setBred(boolean bred);

    boolean isEating();

    void setEating(boolean eating);

    boolean isRearing();

    void setRearing(boolean rearing);

    boolean isMouthOpen();

    void setMouthOpen(boolean mouthOpen);

    Optional<UUID> getOwner();

    void setOwner(UUID owner);

}
