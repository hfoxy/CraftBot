package me.hfox.craftbot.protocol.play.server;

import me.hfox.craftbot.exception.command.BotUnknownCommandParserException;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.play.server.data.command.*;
import me.hfox.craftbot.protocol.play.server.data.command.properties.*;
import me.hfox.craftbot.protocol.play.server.data.command.properties.BrigadierStringCommandNodeProperties.BrigadierStringCommandNodePropertiesType;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static me.hfox.craftbot.protocol.play.server.data.command.CommandNodeType.ARGUMENT;
import static me.hfox.craftbot.protocol.play.server.data.command.CommandNodeType.LITERAL;
import static me.hfox.craftbot.utils.BitUtils.hasFlag;

public class PacketServerPlayDeclareCommands implements ServerPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketServerPlayDeclareCommands.class);

    private CommandNode[] nodes;
    private int rootIndex;

    public CommandNode[] getNodes() {
        return nodes;
    }

    public int getRootIndex() {
        return rootIndex;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        nodes = new CommandNode[buffer.readVarInt()];
        for (int i = 0; i < nodes.length; i++) {
            byte flags = buffer.readByte();

            int nodeTypeId = flags & 0x03;
            if (nodeTypeId >= CommandNodeType.values().length) {
                throw new BotUnknownCommandParserException("Unknown command node type: " + nodeTypeId + " (" + Integer.toBinaryString(nodeTypeId) + ")");
            }

            CommandNodeType nodeType = CommandNodeType.values()[nodeTypeId];

            boolean executable = hasFlag(flags, 0x04);
            boolean redirect = hasFlag(flags, 0x08);
            boolean suggestions = hasFlag(flags, 0x10);
            CommandNodeFlags commandNodeFlags = new CommandNodeFlags(nodeType, executable, redirect, suggestions);

            int[] children = new int[buffer.readVarInt()];
            for (int j = 0; j < children.length; j++) {
                children[j] = buffer.readVarInt();
            }

            Integer redirectNode = redirect ? buffer.readVarInt() : null;
            String name = nodeType == ARGUMENT || nodeType == LITERAL ? buffer.readString() : null;

            String parserId = nodeType == ARGUMENT ? buffer.readString() : null;
            CommandParser parser = parserId != null ? CommandParser.findByIdentifier(parserId) : null;

            CommandNodeProperties properties = null;

            byte propertiesFlags;

            if (nodeType == ARGUMENT) {
                switch (parser) {
                    case BRIGADIER_DOUBLE:
                        propertiesFlags = buffer.readByte();

                        Double minD = null;
                        if (hasFlag(propertiesFlags, 0x01)) {
                            minD = buffer.readDouble();
                        }

                        Double maxD = null;
                        if (hasFlag(propertiesFlags, 0x02)) {
                            maxD = buffer.readDouble();
                        }

                        properties = new BrigadierDoubleCommandNodeProperties(minD, maxD);
                        break;
                    case BRIGADIER_FLOAT:
                        propertiesFlags = buffer.readByte();

                        Float minF = null;
                        if (hasFlag(propertiesFlags, 0x01)) {
                            minF = buffer.readFloat();
                        }

                        Float maxF = null;
                        if (hasFlag(propertiesFlags, 0x02)) {
                            maxF = buffer.readFloat();
                        }

                        properties = new BrigadierFloatCommandNodeProperties(minF, maxF);
                        break;
                    case BRIGADIER_INTEGER:
                        propertiesFlags = buffer.readByte();

                        Integer minI = null;
                        if (hasFlag(propertiesFlags, 0x01)) {
                            minI = buffer.readInt();
                        }

                        Integer maxI = null;
                        if (hasFlag(propertiesFlags, 0x02)) {
                            maxI = buffer.readInt();
                        }

                        properties = new BrigadierIntegerCommandNodeProperties(minI, maxI);
                        break;
                    case BRIGADIER_STRING:
                        BrigadierStringCommandNodePropertiesType type = BrigadierStringCommandNodePropertiesType.values()[buffer.readVarInt()];
                        properties = new BrigadierStringCommandNodeProperties(type);
                        break;
                    case ENTITY:
                        propertiesFlags = buffer.readByte();
                        properties = new EntityCommandNodeProperties(hasFlag(propertiesFlags, 0x01), hasFlag(propertiesFlags, 0x02));
                        break;
                    case SCORE_HOLDER:
                        propertiesFlags = buffer.readByte();
                        properties = new ScoreHolderCommandNodeProperties(hasFlag(propertiesFlags, 0x01));
                        break;
                    case RANGE:
                        propertiesFlags = buffer.readByte();
                        properties = new RangeCommandNodeProperties(hasFlag(propertiesFlags, 0x01));
                        break;
                    default:
                        break;
                }
            }

            String suggestionsTypeId = suggestions ? buffer.readString() : null;
            CommandNodeSuggestionsType suggestionsType = suggestionsTypeId != null ? CommandNodeSuggestionsType.findByIdentifier(suggestionsTypeId) : null;
            CommandNode node = new CommandNode(commandNodeFlags, children, redirectNode, name, parser, properties, suggestionsType);
            nodes[i] = node;
        }

        rootIndex = buffer.readVarInt();
    }

}
