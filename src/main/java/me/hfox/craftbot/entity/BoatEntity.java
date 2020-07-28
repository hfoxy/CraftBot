package me.hfox.craftbot.entity;

public interface BoatEntity extends Entity {

    int getTimeSinceLastHit();

    void setTimeSinceLastHit(int timeSinceLastHit);

    int getForwardDirection();

    void setForwardDirection(int forwardDirection);

    float getDamageTaken();

    void setDamageTaken(float damageTaken);

    BoatEntityType getType();

    void setType(BoatEntityType type);

}
