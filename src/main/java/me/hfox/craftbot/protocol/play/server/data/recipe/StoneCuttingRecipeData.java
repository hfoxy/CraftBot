package me.hfox.craftbot.protocol.play.server.data.recipe;

import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.world.RecipeType;

public class StoneCuttingRecipeData extends RecipeDataImpl {

    private final String group;
    private final IngredientData ingredient;
    private final SlotData result;

    public StoneCuttingRecipeData(String identifier, String group, IngredientData ingredient, SlotData result) {
        super(identifier, RecipeType.STONE_CUTTING);
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
    }

    public String getGroup() {
        return group;
    }

    public IngredientData getIngredient() {
        return ingredient;
    }

    public SlotData getResult() {
        return result;
    }

}
