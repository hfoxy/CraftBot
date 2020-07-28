package me.hfox.craftbot.entity;

import me.hfox.craftbot.world.Direction;
import me.hfox.craftbot.world.Location;

import java.util.Optional;

public interface ShulkerEntity extends GolemEntity {

    Direction getAttachFace();

    void setAttachFace(Direction attachFace);

    Optional<Location> getAttachmentPosition();

    void setAttachmentPosition(Location attachmentPosition);

    byte getShieldHeight();

    void setShieldHeight(byte shieldHeight);

    byte getColour();

    void setColour(byte colour);

}
