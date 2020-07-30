package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.recipe.RecipeAction;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

public class PacketServerPlayUnlockRecipes implements ServerPacket {

    private RecipeAction action;
    private boolean craftingRecipeBookOpen;
    private boolean craftingRecipeBookFilterActive;
    private boolean smeltingRecipeBookOpen;
    private boolean smeltingRecipeBookFilterActive;
    private String[] recipeIds;
    private String[] initRecipeIds;

    public RecipeAction getAction() {
        return action;
    }

    public boolean isCraftingRecipeBookOpen() {
        return craftingRecipeBookOpen;
    }

    public boolean isCraftingRecipeBookFilterActive() {
        return craftingRecipeBookFilterActive;
    }

    public boolean isSmeltingRecipeBookOpen() {
        return smeltingRecipeBookOpen;
    }

    public boolean isSmeltingRecipeBookFilterActive() {
        return smeltingRecipeBookFilterActive;
    }

    public String[] getRecipeIds() {
        return recipeIds;
    }

    public String[] getInitRecipeIds() {
        return initRecipeIds;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        action = RecipeAction.values()[buffer.readVarInt()];
        craftingRecipeBookOpen = buffer.readBoolean();
        craftingRecipeBookFilterActive = buffer.readBoolean();
        smeltingRecipeBookOpen = buffer.readBoolean();
        smeltingRecipeBookFilterActive = buffer.readBoolean();

        recipeIds = new String[buffer.readVarInt()];
        for (int i = 0; i < recipeIds.length; i++) {
            recipeIds[i] = buffer.readString();
        }

        if (action == RecipeAction.INIT) {
            initRecipeIds = new String[buffer.readVarInt()];
            for (int i = 0; i < initRecipeIds.length; i++) {
                initRecipeIds[i] = buffer.readString();
            }
        }
    }

}
