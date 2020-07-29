package me.hfox.craftbot.entity;

import me.hfox.craftbot.entity.particle.ParticleType;

public interface AreaOfEffectCloud extends Entity {

    float getRadius();

    void setRadius(float radius);

    int getColour();

    void setColour(int colour);

    boolean isForceSinglePoint();

    void setForceSinglePoint(boolean forceSinglePoint);

    ParticleType getParticleType();

    void setParticleType(ParticleType particleType);

}
