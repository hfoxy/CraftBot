package me.hfox.craftbot.entity;

import me.hfox.craftbot.world.Location;

public interface EndCrystalEntity extends Entity {

    Location getBeamTarget();

    void setBeamTarget(Location beamTarget);

    boolean isBottomShown();

    void setBottomShown(boolean bottomShown);

}
