package me.hfox.craftbot.entity.particle;

import me.hfox.craftbot.protocol.play.server.data.SlotData;

public interface ItemParticle extends Particle {

    SlotData getItem();

}
