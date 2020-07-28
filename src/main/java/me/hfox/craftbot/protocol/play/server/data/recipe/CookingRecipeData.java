package me.hfox.craftbot.protocol.play.server.data.recipe;

import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.world.RecipeType;

public class CookingRecipeData extends RecipeDataImpl {

    private final String group;
    private final IngredientData ingredient;
    private final SlotData result;
    private final float experience;
    private final int cookingTime;

    public CookingRecipeData(RecipeType recipeType, String identifier, String group, IngredientData ingredient, SlotData result, float experience, int cookingTime) {
        super(identifier, recipeType);
        this.group = group;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
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

    public float getExperience() {
        return experience;
    }

    public int getCookingTime() {
        return cookingTime;
    }

}
