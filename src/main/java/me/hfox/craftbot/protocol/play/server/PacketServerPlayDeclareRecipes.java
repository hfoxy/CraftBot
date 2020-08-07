package me.hfox.craftbot.protocol.play.server;

import io.netty.buffer.Unpooled;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.SlotData;
import me.hfox.craftbot.protocol.play.server.data.recipe.*;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import me.hfox.craftbot.world.RecipeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PacketServerPlayDeclareRecipes implements ServerPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketServerPlayDeclareRecipes.class);

    private ProtocolBuffer buffer;
    private boolean read;

    private RecipeData[] recipes;

    public RecipeData[] getRecipes() throws IOException {
        if (read) {
            return recipes;
        }

        readPopulate();
        return recipes;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        this.buffer = new ProtocolBuffer(Unpooled.buffer());

        byte[] content = new byte[buffer.readableBytes()];
        buffer.readBytes(content);

        this.buffer.writeBytes(content);
    }

    private void readPopulate() throws IOException {
        int recipeCount = buffer.readVarInt();

        recipes = new RecipeData[recipeCount];

        for (int i = 0; i < recipeCount; i++) {
            String recipeTypeString = buffer.readString();
            String recipeId = buffer.readString();

            LOGGER.debug("Recipe ID: {} | Type: {}", recipeId, recipeTypeString);

            RecipeType recipeType = RecipeType.findByTypeId(recipeTypeString);

            RecipeData recipeData;

            String group;
            int ingredientCount;
            IngredientData ingredient;
            IngredientData[] ingredients;
            SlotData result;

            switch (recipeType) {
                case CRAFTING_SHAPELESS:
                    group = buffer.readString();
                    ingredientCount = buffer.readVarInt();
                    ingredients = new IngredientData[ingredientCount];
                    for (int j = 0; j < ingredients.length; j++) {
                        ingredients[j] = buffer.readIngredient();
                    }

                    result = buffer.readSlot();
                    recipeData = new CraftingShapelessRecipeData(recipeId, group, ingredients, result);
                    break;
                case CRAFTING_SHAPED:
                    int width = buffer.readVarInt();
                    int height = buffer.readVarInt();
                    group = buffer.readString();
                    ingredientCount = width * height;
                    ingredients = new IngredientData[ingredientCount];
                    for (int j = 0; j < ingredients.length; j++) {
                        ingredients[j] = buffer.readIngredient();
                    }

                    result = buffer.readSlot();
                    recipeData = new CraftingShapedRecipeData(recipeId, width, height, group, ingredients, result);
                    break;
                case SMELTING:
                case BLASTING:
                case SMOKING:
                case CAMPFIRE_COOKING:
                    group = buffer.readString();
                    ingredient = buffer.readIngredient();
                    result = buffer.readSlot();
                    float experience = buffer.readFloat();
                    int cookingTime = buffer.readVarInt();
                    recipeData = new CookingRecipeData(recipeType, recipeId, group, ingredient, result, experience, cookingTime);
                    break;
                case STONE_CUTTING:
                    group = buffer.readString();
                    ingredient = buffer.readIngredient();
                    result = buffer.readSlot();
                    recipeData = new StoneCuttingRecipeData(recipeId, group, ingredient, result);
                    break;
                default:
                    recipeData = new RecipeDataImpl(recipeId, recipeType);
                    break;
            }

            recipes[i] = recipeData;
        }

        read = true;
        buffer = null;
    }

}
