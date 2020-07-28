package me.hfox.craftbot.protocol;

import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.exception.protocol.BotPacketDecodingException;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.login.client.PacketClientLoginStart;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginDisconnect;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginEncryptionRequest;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginSetCompression;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginSuccess;
import me.hfox.craftbot.protocol.play.server.*;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusPing;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusRequest;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusPong;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusResponse;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

        registerClient(ProtocolState.LOGIN, 0x00, PacketClientLoginStart.class);

        registerServer(ProtocolState.LOGIN, 0x00, PacketServerLoginDisconnect.class);
        registerServer(ProtocolState.LOGIN, 0x01, PacketServerLoginEncryptionRequest.class);
        registerServer(ProtocolState.LOGIN, 0x02, PacketServerLoginSuccess.class);
        registerServer(ProtocolState.LOGIN, 0x03, PacketServerLoginSetCompression.class);

        registerServer(ProtocolState.PLAY, 0x00, PacketServerPlaySpawnEntity.class);
        registerServer(ProtocolState.PLAY, 0x0E, PacketServerPlayServerDifficulty.class);
        registerServer(ProtocolState.PLAY, 0x0F, PacketServerPlayChatMessage.class);
        registerServer(ProtocolState.PLAY, 0x12, PacketServerPlayDeclareCommands.class);
        registerServer(ProtocolState.PLAY, 0x19, PacketServerPlayPluginMessage.class);
        registerServer(ProtocolState.PLAY, 0x1C, PacketServerPlayEntityStatus.class);
        registerServer(ProtocolState.PLAY, 0x26, PacketServerPlayJoinGame.class);
        registerServer(ProtocolState.PLAY, 0x32, PacketServerPlayPlayerAbilities.class);
        registerServer(ProtocolState.PLAY, 0x34, PacketServerPlayPlayerInfo.class);
        registerServer(ProtocolState.PLAY, 0x36, PacketServerPlayPlayerPositionAndLook.class);
        registerServer(ProtocolState.PLAY, 0x37, PacketServerPlayUnlockRecipes.class);
        registerServer(ProtocolState.PLAY, 0x38, PacketServerPlayDestroyEntities.class);
        registerServer(ProtocolState.PLAY, 0x3C, PacketServerPlayEntityHeadLook.class);
        registerServer(ProtocolState.PLAY, 0x3D, PacketServerPlaySelectAdvancementTab.class);
        registerServer(ProtocolState.PLAY, 0x3E, PacketServerPlayWorldBorder.class);
        registerServer(ProtocolState.PLAY, 0x40, PacketServerPlayHeldItemChange.class);
        registerServer(ProtocolState.PLAY, 0x5B, PacketServerPlayDeclareRecipes.class);
        registerServer(ProtocolState.PLAY, 0x5C, PacketServerPlayTags.class);
    }

    @Override
    public void write(Connection connection, ProtocolBuffer buffer, ClientPacket packet) throws BotProtocolException {
        PacketInfo info = getPacketInfo(packet);
        int packetId = info.getPacketId();

        buffer.writeVarInt(packetId);

        try {
            packet.write(buffer);
        } catch (IOException ex) {
            throw new BotProtocolException("Unable to write Packet", ex);
        }
    }

    @Override
    public ServerPacket read(Connection connection, ProtocolBuffer buffer) throws BotProtocolException {
        int packetId = buffer.readVarInt();

        Class<? extends ServerPacket> packetType = null;
        ServerPacket packet;

        try {
            packetType = getServerClassById(state, packetId);
            packet = packetType.getDeclaredConstructor().newInstance();

            packet.read(buffer);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            throw new BotPacketDecodingException("Unable to construct Packet", ex);
        } catch (IOException ex) {
            throw new BotProtocolException("Unable to read Packet", ex);
        } finally {
            if (buffer.readableBytes() > 0) {
                String packetName = packetType == null ? "#" + Integer.toHexString(packetId) : packetType.getSimpleName();
                LOGGER.warn("Reading of {} left {} bytes", packetName, buffer.readableBytes());

                // read off the rest of the bytes so that it doesn't break subsequent packets
                byte[] remaining = new byte[buffer.readableBytes()];
                buffer.readBytes(remaining);
            }
        }

        return packet;
    }

}
