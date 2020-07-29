package me.hfox.craftbot.entity.projectile;

import me.hfox.craftbot.entity.Entity;
import me.hfox.craftbot.protocol.play.server.data.SlotData;

public interface SmallFireball extends Entity {

    SlotData getItem();

    void setItem(SlotData item);

}
