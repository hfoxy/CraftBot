package me.hfox.craftbot.entity.thrown;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.protocol.play.server.data.SlotData;

public interface ThrownItem extends Entity {

    SlotData getItem();

    void setItem(SlotData item);

}
