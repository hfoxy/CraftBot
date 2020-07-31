package me.hfox.craftbot.world.palette;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.hfox.craftbot.utils.ToStringBuilder;

import java.util.List;
import java.util.Map;

public class BlockDto {

    @JsonIgnore
    private String identifier;

    private List<BlockStateDto> states;
    private Map<String, List<String>> properties;

    public BlockDto() {
        // jackson
    }

    public BlockDto(List<BlockStateDto> states) {
        this.states = states;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<BlockStateDto> getStates() {
        return states;
    }

    public void setStates(List<BlockStateDto> states) {
        this.states = states;
    }

    public Map<String, List<String>> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, List<String>> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return ToStringBuilder.build(this).deepArray(true).parents(true).reflective(true).simpleName(true).excludes("states").toString();
    }

}
