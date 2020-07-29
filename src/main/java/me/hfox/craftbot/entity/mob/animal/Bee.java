package me.hfox.craftbot.entity.mob.animal;

import me.hfox.craftbot.entity.mob.animal.Animal;

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
