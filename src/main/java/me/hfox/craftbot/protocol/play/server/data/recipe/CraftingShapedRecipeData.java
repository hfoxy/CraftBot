package me.hfox.craftbot.protocol.play.server.data.recipe;

import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.world.RecipeType;

public class CraftingShapedRecipeData extends RecipeDataImpl {

    private final int width;
    private final int height;
    private final String group;
    private final IngredientData[] ingredients;
    private final SlotData result;

    public CraftingShapedRecipeData(String identifier, int width, int height, String group, IngredientData[] ingredients, SlotData result) {
        super(identifier, RecipeType.CRAFTING_SHAPED);
        this.width = width;
        this.height = height;
        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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
