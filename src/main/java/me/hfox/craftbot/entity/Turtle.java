package me.hfox.craftbot.entity;

import me.hfox.craftbot.world.Location;

public interface Turtle extends Animal {

    Location getHomePosition();

    void setHomePosition(Location homePosition);

    boolean hasEgg();

    void setEgg(boolean egg);

    boolean isLayingEgg();

    void setLayingEgg(boolean layingEgg);

    Location getTravelPosition();

    void setTravelPosition(Location travelPosition);

    boolean isGoingHome();

    void setGoingHome(boolean goingHome);

    boolean isTravelling();

    void setTravelling(boolean travelling);

}
