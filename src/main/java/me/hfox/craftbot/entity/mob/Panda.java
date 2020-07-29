package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.Animal;

public interface Panda extends Animal {

    int getBreedTimer();

    void setBreedTimer(int breedTimer);

    int getSneezeTimer();

    void setSneezeTimer(int sneezeTimer);

    int getEatTimer();

    void setEatTimer(int eatTimer);

    byte getMainGene();

    void setMainGene(byte mainGene);

    byte getHiddenGene();

    void setHiddenGene(byte hiddenGene);

    boolean isSneezing();

    void setSneezing(boolean sneezing);

    boolean isRolling();

    void setRolling(boolean rolling);

    boolean isSitting();

    void setSitting(boolean sitting);

    boolean isOnBack();

    void setOnBack(boolean onBack);

}
