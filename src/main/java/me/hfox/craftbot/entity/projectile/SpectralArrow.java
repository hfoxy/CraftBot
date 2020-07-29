package me.hfox.craftbot.entity.projectile;

public interface SpectralArrow extends Arrow {

    default int getColour() {
        throw new UnsupportedOperationException();
    }

    default void setColour(int colour) {
        throw new UnsupportedOperationException();
    }

}
