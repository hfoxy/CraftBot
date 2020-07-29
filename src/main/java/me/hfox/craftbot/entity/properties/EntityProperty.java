package me.hfox.craftbot.entity.properties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityProperty {

    private final String key;
    private final double value;
    private final List<EntityPropertyModifier> modifiers;

    public EntityProperty(String key, double value, List<EntityPropertyModifier> modifiers) {
        this.key = key;
        this.value = value;

        Collections.sort(modifiers);
        this.modifiers = modifiers;
    }

    public String getKey() {
        return key;
    }

    public double getValue() {
        return value;
    }

    public List<EntityPropertyModifier> getModifiers() {
        return new ArrayList<>(modifiers);
    }

}
