package me.hfox.craftbot.world.palette;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class BlockStateDto {

    private int id;

    @JsonProperty("default")
    private boolean def;

    private Map<String, String> properties;

    public BlockStateDto() {
        // jackson
    }

    public BlockStateDto(int id, boolean def) {
        this.id = id;
        this.def = def;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDefault() {
        return def;
    }

    public void setDefault(boolean def) {
        this.def = def;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

}
