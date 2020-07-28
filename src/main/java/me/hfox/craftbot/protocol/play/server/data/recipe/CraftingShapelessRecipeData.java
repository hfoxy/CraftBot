package me.hfox.craftbot.protocol.play.server.data.recipe;

import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.world.RecipeType;

public class CraftingShapelessRecipeData extends RecipeDataImpl {

    private final String group;
    private final IngredientData[] ingredients;
    private final SlotData result;

    public CraftingShapelessRecipeData(String identifier, String group, IngredientData[] ingredients, SlotData result) {
        super(identifier, RecipeType.CRAFTING_SHAPELESS);
        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
    }

    public String getGroup() {
        return group;
    }

    public IngredientData[] getIngredients() {
        return ingredients;
    }

    public SlotData getResult() {
        return result;
    }

}
