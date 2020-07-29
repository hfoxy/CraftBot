package me.hfox.craftbot.entity.properties;

import java.util.UUID;

public class EntityPropertyModifier implements Comparable<EntityPropertyModifier> {

    private final UUID uniqueId;
    private final double value;
    private final EntityPropertyModifierOperation operation;

    public EntityPropertyModifier(UUID uniqueId, double value, EntityPropertyModifierOperation operation) {
        this.uniqueId = uniqueId;
        this.value = value;
        this.operation = operation;
    }

    public UUID getUniqueId() {
        return uniqueId;
    }

    public double getValue() {
        return value;
    }

    public EntityPropertyModifierOperation getOperation() {
        return operation;
    }

    @Override
    public int compareTo(EntityPropertyModifier o) {
        return getOperation().ordinal() - o.getOperation().ordinal();
    }

}
