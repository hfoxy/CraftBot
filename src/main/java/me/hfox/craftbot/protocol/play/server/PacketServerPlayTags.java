package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;

import java.io.IOException;

public class PacketServerPlayTags implements ServerPacket {

    private Tag[] blockTags;
    private Tag[] itemTags;
    private Tag[] fluidTags;
    private Tag[] entityTags;

    public Tag[] getBlockTags() {
        return blockTags;
    }

    public Tag[] getItemTags() {
        return itemTags;
    }

    public Tag[] getFluidTags() {
        return fluidTags;
    }

    public Tag[] getEntityTags() {
        return entityTags;
    }

    @Override
    public void read(ProtocolBuffer buffer) throws IOException, BotProtocolException {
        blockTags = readTags(buffer);
        itemTags = readTags(buffer);
        fluidTags = readTags(buffer);
        entityTags = readTags(buffer);
    }

    private Tag[] readTags(ProtocolBuffer buffer) {
        Tag[] tags = new Tag[buffer.readVarInt()];
        for (int i = 0; i < tags.length; i++) {
            String name = buffer.readString();
            int[] entries = new int[buffer.readVarInt()];
            for (int j = 0; j < entries.length; j++) {
                entries[j] = buffer.readVarInt();
            }

            tags[i] = new Tag(name, entries);
        }

        return tags;
    }

    public static class Tag {

        private final String name;
        private final int[] entries;

        public Tag(String name, int[] entries) {
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
