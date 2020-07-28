package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.recipe.RecipeAction;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayUnlockRecipes implements ServerPacket {

    private RecipeAction action;
    private boolean craftingRecipeBookOpen;
    private boolean craftingRecipeBookFilterActive;
    private boolean smeltingRecipeBookOpen;
    private boolean smeltingRecipeBookFilterActive;
    private int[] recipeIds;
    private String[] recipeIdentifiers;

    public RecipeAction getAction() {
        return action;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        action = RecipeAction.values()[buffer.readVarInt()];
        craftingRecipeBookOpen = buffer.readBoolean();
        craftingRecipeBookFilterActive = buffer.readBoolean();
        smeltingRecipeBookOpen = buffer.readBoolean();
        smeltingRecipeBookFilterActive = buffer.readBoolean();

        recipeIds = new int[buffer.readVarInt()];
        for (int i = 0; i < recipeIds.length; i++) {
            recipeIds[i] = buffer.readVarInt();
        }

        if (action == RecipeAction.INIT) {
            recipeIdentifiers = new String[buffer.readVarInt()];
            for (int i = 0; i < recipeIdentifiers.length; i++) {
                recipeIdentifiers[i] = buffer.readString();
            }
        }
    }

}
