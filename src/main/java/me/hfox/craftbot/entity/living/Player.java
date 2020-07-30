package me.hfox.craftbot.entity.living;

import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.entity.data.DisplayedSkinParts;
import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.nbt.Tag;
import me.hfox.craftbot.player.Gamemode;
import me.hfox.craftbot.protocol.play.server.data.player.PlayerInfoProperty;

import java.util.Optional;

public interface Player extends LivingEntity {

    String getName();

    Optional<ChatComponent> getDisplayName();

    boolean isDisplayNameSet();

    PlayerInfoProperty[] getProperties();

    Gamemode getGameMode();

    int getPing();

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
