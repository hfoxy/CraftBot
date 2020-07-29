package me.hfox.craftbot.entity.living;

import me.hfox.craftbot.entity.data.DisplayedSkinParts;
import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.nbt.Tag;

public interface Player extends LivingEntity {

    float getAdditionalHearts();

    void setAdditionalHearts(float additionalHearts);

    int getScore();

    void setScore(int score);

    DisplayedSkinParts getSkinParts();

    void setSkinParts(DisplayedSkinParts skinParts);

    Hand getMainHand();

    void setMainHand(Hand hand);

    Tag getLeftShoulderEntity();

    void setLeftShoulderEntity(Tag tag);

    Tag getRightShoulderEntity();

    void setRightShoulderEntity(Tag tag);

}
