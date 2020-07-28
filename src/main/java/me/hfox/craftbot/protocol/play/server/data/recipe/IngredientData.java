package me.hfox.craftbot.protocol.play.server.data.recipe;

import me.hfox.craftbot.protocol.play.server.data.SlotData;

public class IngredientData {

    private final SlotData[] items;

    public IngredientData(SlotData[] items) {
        this.items = items;
    }

    public SlotData[] getItems() {
        return items;
    }

}
