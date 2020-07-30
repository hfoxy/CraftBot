package me.hfox.craftbot.entity;

import me.hfox.craftbot.protocol.play.server.data.SlotData;

import java.util.Optional;

public interface ItemFrame extends Entity {

    Optional<SlotData> getItem();

    void setItem(SlotData item);

    int getRotation();

    void setRotation(int rotation);

}
