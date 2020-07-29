package me.hfox.craftbot.entity.living.mob.animal;

public interface Bee extends Animal {

    boolean isAngry();

    void setAngry(boolean angry);

    boolean hasStung();

    void setStung(boolean stung);

    boolean hasNectar();

    void setNectar(boolean nectar);

    int getAngerTime();

    void setAngerTime(int angerTime);

}
