package me.hfox.craftbot.entity.mob.animal;

import me.hfox.craftbot.entity.mob.animal.ChestedHorse;

public interface Llama extends ChestedHorse {

    int getStrength();

    void setStrength(int strength);

    int getCarpetColour();

    void setCarpetColour(int carpetColour);

    int getVariant();

    void setVariant(int variant);

}
