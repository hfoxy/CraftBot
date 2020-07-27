package me.hfox.craftbot.protocol;

import me.hfox.craftbot.exception.BotPacketRegistrationException;
import me.hfox.craftbot.exception.BotProtocolException;
import me.hfox.craftbot.exception.BotUnknownPacketException;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public abstract class RegistryProtocol {

    private final Map<Class<? extends Packet>, ProtocolState> packetStates;

    private final EnumMap<ProtocolState, Map<Class<? extends ClientPacket>, Integer>> clientPacketsToIdMap;
    private final EnumMap<ProtocolState, Map<Integer, Class<? extends ClientPacket>>> idToClientPacketsMap;

    private final EnumMap<ProtocolState, Map<Class<? extends ServerPacket>, Integer>> serverPacketsToIdMap;
    private final EnumMap<ProtocolState, Map<Integer, Class<? extends ServerPacket>>> idToServerPacketsMap;

    public RegistryProtocol() throws BotProtocolException {
        packetStates = new HashMap<>();

        clientPacketsToIdMap = new EnumMap<>(ProtocolState.class);
        idToClientPacketsMap = new EnumMap<>(ProtocolState.class);
        serverPacketsToIdMap = new EnumMap<>(ProtocolState.class);
        idToServerPacketsMap = new EnumMap<>(ProtocolState.class);

        for (ProtocolState state : ProtocolState.values()) {
            clientPacketsToIdMap.put(state, new HashMap<>());
            idToClientPacketsMap.put(state, new HashMap<>());
            serverPacketsToIdMap.put(state, new HashMap<>());
            idToServerPacketsMap.put(state, new HashMap<>());
        }

        register();
    }

    protected abstract void register() throws BotProtocolException;

    protected PacketInfo getPacketInfo(Packet packet) throws BotUnknownPacketException {
        Class<? extends Packet> packetType = packet.getClass();
        ProtocolState state = packetStates.get(packetType);
        if (state == null) {
            throw new BotUnknownPacketException("Unknown Packet for class: " + packetType.getName());
        }

        if (packet instanceof ClientPacket) {
            return new PacketInfo(state, clientPacketsToIdMap.get(state).get(packetType), packetType);
        } else if (packet instanceof ServerPacket) {
            return new PacketInfo(state, serverPacketsToIdMap.get(state).get(packetType), packetType);
        } else {
            throw new BotUnknownPacketException("Unknown Packet type: " + packet.getClass().getName());
        }
    }

    protected Class<? extends ClientPacket> getClientClassById(ProtocolState state, int id) throws BotUnknownPacketException {
        Class<? extends ClientPacket> packetType = idToClientPacketsMap.get(state).get(id);
        if (packetType == null) {
            throw new BotUnknownPacketException("Unknown Packet " + state.name() + " #" + id);
        }

        return packetType;
    }

    protected Class<? extends ServerPacket> getServerClassById(ProtocolState state, int id) throws BotUnknownPacketException {
        Class<? extends ServerPacket> packetType = idToServerPacketsMap.get(state).get(id);
        if (packetType == null) {
            throw new BotUnknownPacketException("Unknown Packet " + state.name() + " #" + id);
        }

        return packetType;
    }

    protected void registerClient(ProtocolState state, int id, Class<? extends ClientPacket> packet) throws BotProtocolException {
        Map<Class<? extends ClientPacket>, Integer> clientPacketsToId = clientPacketsToIdMap.get(state);
        Map<Integer, Class<? extends ClientPacket>> idToClientPackets = idToClientPacketsMap.get(state);

        if (idToClientPackets.containsKey(id)) {
            throw new BotPacketRegistrationException("Packet already registered");
        }

        clientPacketsToId.put(packet, id);
        idToClientPackets.put(id, packet);
        packetStates.put(packet, state);
    }

    protected void registerServer(ProtocolState state, int id, Class<? extends ServerPacket> packet) throws BotProtocolException {
        Map<Class<? extends ServerPacket>, Integer> serverPacketsToId = serverPacketsToIdMap.get(state);
        Map<Integer, Class<? extends ServerPacket>> idToServerPackets = idToServerPacketsMap.get(state);

        if (idToServerPackets.containsKey(id)) {
            throw new BotPacketRegistrationException("Packet already registered");
        }

        serverPacketsToId.put(packet, id);
        idToServerPackets.put(id, packet);
        packetStates.put(packet, state);
    }

}
