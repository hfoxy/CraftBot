package me.hfox.craftbot.entity;

public interface BeeEntity extends AnimalEntity {

    boolean isAngry();

    void setAngry(boolean angry);

    boolean hasStung();

    void setStung(boolean stung);

    boolean hasNectar();

    void setNectar(boolean nectar);

    int getAngerTime();

    void setAngerTime(int angerTime);

}
