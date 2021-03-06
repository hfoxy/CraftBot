package me.hfox.craftbot.protocol;

import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.exception.protocol.BotPacketDecodingException;
import me.hfox.craftbot.exception.protocol.BotProtocolException;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.login.client.PacketClientLoginEncryptionResponse;
import me.hfox.craftbot.protocol.login.client.PacketClientLoginStart;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginDisconnect;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginEncryptionRequest;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginSetCompression;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginSuccess;
import me.hfox.craftbot.protocol.play.client.*;
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
        registerClient(ProtocolState.LOGIN, 0x01, PacketClientLoginEncryptionResponse.class);

        registerServer(ProtocolState.LOGIN, 0x00, PacketServerLoginDisconnect.class);
        registerServer(ProtocolState.LOGIN, 0x01, PacketServerLoginEncryptionRequest.class);
        registerServer(ProtocolState.LOGIN, 0x02, PacketServerLoginSuccess.class);
        registerServer(ProtocolState.LOGIN, 0x03, PacketServerLoginSetCompression.class);

        registerClient(ProtocolState.PLAY, 0x00, PacketClientPlayTeleportConfirm.class);
        registerClient(ProtocolState.PLAY, 0x03, PacketClientPlayChatMessage.class);
        registerClient(ProtocolState.PLAY, 0x04, PacketClientPlayClientStatus.class);
        registerClient(ProtocolState.PLAY, 0x05, PacketClientPlayClientSettings.class);
        registerClient(ProtocolState.PLAY, 0x0F, PacketClientPlayKeepAlive.class);
        registerClient(ProtocolState.PLAY, 0x11, PacketClientPlayPlayerPosition.class);
        registerClient(ProtocolState.PLAY, 0x12, PacketClientPlayPlayerPositionAndRotation.class);
        registerClient(ProtocolState.PLAY, 0x14, PacketClientPlayPlayerMovement.class);
        registerClient(ProtocolState.PLAY, 0x1B, PacketClientPlayEntityAction.class);
        registerClient(ProtocolState.PLAY, 0x24, PacketClientPlayUpdateCommandBlock.class);

        registerServer(ProtocolState.PLAY, 0x00, PacketServerPlaySpawnEntity.class);
        registerServer(ProtocolState.PLAY, 0x03, PacketServerPlaySpawnLivingEntity.class);
        registerServer(ProtocolState.PLAY, 0x05, PacketServerPlaySpawnPlayer.class);
        registerServer(ProtocolState.PLAY, 0x06, PacketServerPlayEntityAnimation.class);
        registerServer(ProtocolState.PLAY, 0x09, PacketServerPlayBlockBreakAnimation.class);
        registerServer(ProtocolState.PLAY, 0x0B, PacketServerPlayBlockAction.class);
        registerServer(ProtocolState.PLAY, 0x0C, PacketServerPlayBlockChange.class);
        registerServer(ProtocolState.PLAY, 0x0E, PacketServerPlayServerDifficulty.class);
        registerServer(ProtocolState.PLAY, 0x0F, PacketServerPlayChatMessage.class);
        registerServer(ProtocolState.PLAY, 0x10, PacketServerPlayMultiBlockChange.class);
        registerServer(ProtocolState.PLAY, 0x12, PacketServerPlayDeclareCommands.class);
        registerServer(ProtocolState.PLAY, 0x15, PacketServerPlayWindowItems.class);
        registerServer(ProtocolState.PLAY, 0x17, PacketServerPlaySetSlot.class);
        registerServer(ProtocolState.PLAY, 0x18, PacketServerPlaySetCooldown.class);
        registerServer(ProtocolState.PLAY, 0x19, PacketServerPlayPluginMessage.class);
        registerServer(ProtocolState.PLAY, 0x1B, PacketServerPlayDisconnect.class);
        registerServer(ProtocolState.PLAY, 0x1F, PacketServerPlayChangeGameState.class);
        registerServer(ProtocolState.PLAY, 0x1C, PacketServerPlayEntityStatus.class);
        registerServer(ProtocolState.PLAY, 0x1E, PacketServerPlayUnloadChunk.class);
        registerServer(ProtocolState.PLAY, 0x21, PacketServerPlayKeepAlive.class);
        registerServer(ProtocolState.PLAY, 0x22, PacketServerPlayChunkData.class);
        registerServer(ProtocolState.PLAY, 0x23, PacketServerPlayEffect.class);
        registerServer(ProtocolState.PLAY, 0x24, PacketServerPlayParticle.class);
        registerServer(ProtocolState.PLAY, 0x25, PacketServerPlayUpdateLight.class);
        registerServer(ProtocolState.PLAY, 0x26, PacketServerPlayJoinGame.class);
        registerServer(ProtocolState.PLAY, 0x29, PacketServerPlayEntityPosition.class);
        registerServer(ProtocolState.PLAY, 0x2A, PacketServerPlayEntityPositionAndRotation.class);
        registerServer(ProtocolState.PLAY, 0x2B, PacketServerPlayEntityRotation.class);
        registerServer(ProtocolState.PLAY, 0x32, PacketServerPlayPlayerAbilities.class);
        registerServer(ProtocolState.PLAY, 0x34, PacketServerPlayPlayerInfo.class);
        registerServer(ProtocolState.PLAY, 0x36, PacketServerPlayPlayerPositionAndLook.class);
        registerServer(ProtocolState.PLAY, 0x37, PacketServerPlayUnlockRecipes.class);
        registerServer(ProtocolState.PLAY, 0x38, PacketServerPlayDestroyEntities.class);
        registerServer(ProtocolState.PLAY, 0x3B, PacketServerPlayRespawn.class);
        registerServer(ProtocolState.PLAY, 0x3C, PacketServerPlayEntityHeadLook.class);
        registerServer(ProtocolState.PLAY, 0x3D, PacketServerPlaySelectAdvancementTab.class);
        registerServer(ProtocolState.PLAY, 0x3E, PacketServerPlayWorldBorder.class);
        registerServer(ProtocolState.PLAY, 0x40, PacketServerPlayHeldItemChange.class);
        registerServer(ProtocolState.PLAY, 0x41, PacketServerPlayUpdateViewPosition.class);
        registerServer(ProtocolState.PLAY, 0x42, PacketServerPlayUpdateViewDistance.class);
        registerServer(ProtocolState.PLAY, 0x44, PacketServerPlayEntityMetadata.class);
        registerServer(ProtocolState.PLAY, 0x45, PacketServerPlayAttachEntity.class);
        registerServer(ProtocolState.PLAY, 0x46, PacketServerPlayEntityVelocity.class);
        registerServer(ProtocolState.PLAY, 0x47, PacketServerPlayEntityEquipment.class);
        registerServer(ProtocolState.PLAY, 0x48, PacketServerPlaySetExperience.class);
        registerServer(ProtocolState.PLAY, 0x49, PacketServerPlayUpdateHealth.class);
        registerServer(ProtocolState.PLAY, 0x4B, PacketServerPlaySetPassengers.class);
        registerServer(ProtocolState.PLAY, 0x4E, PacketServerPlaySpawnPosition.class);
        registerServer(ProtocolState.PLAY, 0x4F, PacketServerPlayTimeUpdate.class);
        registerServer(ProtocolState.PLAY, 0x50, PacketServerPlayTitle.class);
        registerServer(ProtocolState.PLAY, 0x52, PacketServerPlaySoundEffect.class);
        registerServer(ProtocolState.PLAY, 0x53, PacketServerPlayStopSound.class);
        registerServer(ProtocolState.PLAY, 0x54, PacketServerPlayPlayerListHeaderFooter.class);
        registerServer(ProtocolState.PLAY, 0x57, PacketServerPlayEntityTeleport.class);
        registerServer(ProtocolState.PLAY, 0x58, PacketServerPlayAdvancements.class);
        registerServer(ProtocolState.PLAY, 0x59, PacketServerPlayEntityProperties.class);
        registerServer(ProtocolState.PLAY, 0x5A, PacketServerPlayEntityEffect.class);
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
                String packetName = packetType == null ? state + "#" + Integer.toHexString(packetId) : packetType.getSimpleName();
                LOGGER.warn("Reading of {} left {} bytes", packetName, buffer.readableBytes());

                // read off the rest of the bytes so that it doesn't break subsequent packets
                byte[] remaining = new byte[buffer.readableBytes()];
                buffer.readBytes(remaining);
            }
        }

        return packet;
    }

}
