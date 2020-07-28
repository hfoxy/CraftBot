package me.hfox.craftbot.protocol.play.server.data.recipe;

import me.hfox.craftbot.world.RecipeType;

public interface RecipeData {

    String getIdentifier();

    RecipeType getType();

}
