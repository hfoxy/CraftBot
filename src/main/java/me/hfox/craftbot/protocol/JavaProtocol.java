package me.hfox.craftbot.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.exception.BotPacketDecodingException;
import me.hfox.craftbot.exception.BotProtocolException;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusPing;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusRequest;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusPong;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusResponse;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class JavaProtocol extends RegistryProtocol implements Protocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaProtocol.class);

    private ProtocolState state;

    public JavaProtocol() throws BotProtocolException {
        super();
        this.state = ProtocolState.HANDSHAKE;
    }

    @Override
    public ProtocolState getState() {
        return state;
    }

    @Override
    public void setState(ProtocolState state) {
        this.state = state;
    }

    @Override
    protected void register() throws BotProtocolException {
        registerClient(ProtocolState.HANDSHAKE, 0x00, PacketClientHandshake.class);

        registerClient(ProtocolState.STATUS, 0x00, PacketClientStatusRequest.class);
        registerClient(ProtocolState.STATUS, 0x01, PacketClientStatusPing.class);

        registerServer(ProtocolState.STATUS, 0x00, PacketServerStatusResponse.class);
        registerServer(ProtocolState.STATUS, 0x01, PacketServerStatusPong.class);
    }

    @Override
    public void write(Connection connection, ProtocolBuffer buffer, ClientPacket packet) throws BotProtocolException {
        PacketInfo info = getPacketInfo(packet);
        int packetId = info.getPacketId();

        buffer.writeVarInt(packetId);
        packet.write(buffer);
    }

    @Override
    public ServerPacket read(Connection connection, ProtocolBuffer buffer) throws BotProtocolException {
        int packetId = buffer.readVarInt();

        Class<? extends ServerPacket> packetType = getServerClassById(state, packetId);
        ServerPacket packet;

        try {
            packet = packetType.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            throw new BotPacketDecodingException("Unable to construct Packet", ex);
        }

        packet.read(buffer);
        return packet;
    }

}
