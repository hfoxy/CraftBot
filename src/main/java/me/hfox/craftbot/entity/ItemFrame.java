package me.hfox.craftbot.entity;

import me.hfox.craftbot.protocol.play.server.data.SlotData;

public interface ItemFrame extends Entity {

    SlotData getItem();

    void setItem(SlotData item);

    int getRotation();

    void setRotation(int rotation);

}
