package me.hfox.craftbot.entity.data;

import me.hfox.craftbot.utils.ToStringBuilder;

public class DisplayedSkinParts {

    private final boolean cape;
    private final boolean jacket;
    private final boolean leftSleeve;
    private final boolean rightSleeve;
    private final boolean leftTrouserLeg;
    private final boolean rightTrouserLeg;
    private final boolean hat;

    public DisplayedSkinParts(boolean cape, boolean jacket, boolean leftSleeve, boolean rightSleeve, boolean leftTrouserLeg, boolean rightTrouserLeg, boolean hat) {
        this.cape = cape;
        this.jacket = jacket;
        this.leftSleeve = leftSleeve;
        this.rightSleeve = rightSleeve;
        this.leftTrouserLeg = leftTrouserLeg;
        this.rightTrouserLeg = rightTrouserLeg;
        this.hat = hat;
    }

    public boolean isCape() {
        return cape;
    }

    public boolean isJacket() {
        return jacket;
    }

    public boolean isLeftSleeve() {
        return leftSleeve;
    }

    public boolean isRightSleeve() {
        return rightSleeve;
    }

    public boolean isLeftTrouserLeg() {
        return leftTrouserLeg;
    }

    public boolean isRightTrouserLeg() {
        return rightTrouserLeg;
    }

    public boolean isHat() {
        return hat;
    }

    @Override
    public String toString() {
        return ToStringBuilder.build(this).deepArray(true).parents(true).reflective(true).simpleName(true).toString();
    }

}
