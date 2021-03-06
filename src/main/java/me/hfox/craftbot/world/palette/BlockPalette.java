package me.hfox.craftbot.world.palette;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.hfox.craftbot.CraftBotStart;
import me.hfox.craftbot.exception.world.BotUnknownBlockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class BlockPalette {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlockPalette.class);

    private static final Map<Integer, BlockStateDto> BLOCK_STATES = new HashMap<>();

    private static int BIGGEST_ID;
    private static int AIR_ID;
    private static BlockStateDto AIR;

    public static void load() throws IOException {
        LOGGER.info("Parsing palette file");
        InputStream paletteInput;
        File file = new File("palette.json");
        if (file.exists()) {
            paletteInput = new FileInputStream(file);
        } else {
            paletteInput = CraftBotStart.class.getResourceAsStream("/palette.json");
        }

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, BlockDto>> typeRef = new TypeReference<HashMap<String, BlockDto>>() {};

        Map<String, BlockDto> map = mapper.readValue(paletteInput, typeRef);
        Set<BlockStateDto> states = new HashSet<>();
        for (Map.Entry<String, BlockDto> entry : map.entrySet()) {
            BlockDto block = entry.getValue();
            block.setIdentifier(entry.getKey());

            for (BlockStateDto state : block.getStates()) {
                state.setBlock(block);
                BlockStateDto old = BLOCK_STATES.put(state.getId(), state);
                if (old != null) {
                    throw new BotUnknownBlockException("Block ID " + state.getId() + " already exists");
                }

                if (state.getId() > BIGGEST_ID) {
                    BIGGEST_ID = state.getId();
                }

                if (entry.getKey().equals("minecraft:air")) {
                    AIR_ID = state.getId();
                }
            }

            states.addAll(block.getStates());
        }

        LOGGER.info("Loaded {} blocks for a total of {} states (highest block #{})", map.size(), states.size(), BIGGEST_ID);
    }

    public static Optional<BlockStateDto> findById(int blockId) {
        return Optional.ofNullable(BLOCK_STATES.get(blockId));
    }

    public static int getBiggestId() {
        return BIGGEST_ID;
    }

    public static BlockStateDto getAir() {
        if (AIR != null) {
            return AIR;
        }

        AIR = findById(AIR_ID).orElse(null);
        return AIR;
    }

}
