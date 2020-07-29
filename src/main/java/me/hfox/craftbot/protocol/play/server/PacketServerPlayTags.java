package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayTags implements ServerPacket {

    private TagData[] blockTags;
    private TagData[] itemTags;
    private TagData[] fluidTags;
    private TagData[] entityTags;

    public TagData[] getBlockTags() {
        return blockTags;
    }

    public TagData[] getItemTags() {
        return itemTags;
    }

    public TagData[] getFluidTags() {
        return fluidTags;
    }

    public TagData[] getEntityTags() {
        return entityTags;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        blockTags = readTags(buffer);
        itemTags = readTags(buffer);
        fluidTags = readTags(buffer);
        entityTags = readTags(buffer);
    }

    private TagData[] readTags(ProtocolBuffer buffer) {
        TagData[] tags = new TagData[buffer.readVarInt()];
        for (int i = 0; i < tags.length; i++) {
            String name = buffer.readString();
            int[] entries = new int[buffer.readVarInt()];
            for (int j = 0; j < entries.length; j++) {
                entries[j] = buffer.readVarInt();
            }

            tags[i] = new TagData(name, entries);
        }

        return tags;
    }

    public static class TagData {

        private final String name;
        private final int[] entries;

        public TagData(String name, int[] entries) {
            this.name = name;
            this.entries = entries;
        }

        public String getName() {
            return name;
        }

        public int[] getEntries() {
            return entries;
        }

    }

}
