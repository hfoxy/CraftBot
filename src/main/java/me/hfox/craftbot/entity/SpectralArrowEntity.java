package me.hfox.craftbot.entity;

public interface SpectralArrowEntity extends ArrowEntity {

    default int getColour() {
        throw new UnsupportedOperationException();
    }

    default void setColour(int colour) {
        throw new UnsupportedOperationException();
    }

}
