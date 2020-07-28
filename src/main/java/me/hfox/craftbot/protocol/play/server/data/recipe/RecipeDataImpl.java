package me.hfox.craftbot.protocol.play.server.data.recipe;

import me.hfox.craftbot.world.RecipeType;

public class RecipeDataImpl implements RecipeData {

    private final String identifier;
    private final RecipeType type;

    public RecipeDataImpl(String identifier, RecipeType type) {
        this.identifier = identifier;
        this.type = type;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public RecipeType getType() {
        return type;
    }

}
