package me.hfox.craftbot.entity.mob;

import me.hfox.craftbot.entity.mob.ChestedHorse;

public interface Llama extends ChestedHorse {

    int getStrength();

    void setStrength(int strength);

    int getCarpetColour();

    void setCarpetColour(int carpetColour);

    int getVariant();

    void setVariant(int variant);

}
