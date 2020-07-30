package me.hfox.craftbot.world.palette;

import java.util.List;
import java.util.Map;

public class BlockDto {

    private List<BlockStateDto> states;
    private Map<String, List<String>> properties;

    public BlockDto() {
        // jackson
    }

    public BlockDto(List<BlockStateDto> states) {
        this.states = states;
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

}
