package me.hfox.craftbot.entity;

import me.hfox.craftbot.world.Rotation;

public interface ArmourStandEntity extends LivingEntity {

    boolean isSmall();

    void setSmall(boolean small);

    boolean hasArms();

    void setArms(boolean arms);

    boolean hasBasePlate();

    void setBasePlate(boolean basePlate);

    boolean isMarker();

    void setMarker(boolean marker);

    Rotation getHeadRotation();

    void setHeadRotation(Rotation headRotation);

    Rotation getBodyRotation();

    void setBodyRotation(Rotation bodyRotation);

    Rotation getLeftArmRotation();

    void setLeftArmRotation(Rotation leftArmRotation);

    Rotation getRightArmRotation();

    void setRightArmRotation(Rotation rightArmRotation);

    Rotation getLeftLegRotation();

    void setLeftLegRotation(Rotation leftLegRotation);

    Rotation getRightLegRotation();

    void setRightLegRotation(Rotation rightLegRotation);

}
