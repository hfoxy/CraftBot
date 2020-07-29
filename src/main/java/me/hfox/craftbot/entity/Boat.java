package me.hfox.craftbot.entity;

public interface Boat extends Entity {

    int getTimeSinceLastHit();

    void setTimeSinceLastHit(int timeSinceLastHit);

    int getForwardDirection();

    void setForwardDirection(int forwardDirection);

    float getDamageTaken();

    void setDamageTaken(float damageTaken);

    BoatType getType();

    void setType(BoatType type);

}
