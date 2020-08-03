package me.hfox.craftbot.connection;

import io.netty.channel.socket.SocketChannel;
import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.connection.compression.Compression;
import me.hfox.craftbot.connection.compression.CompressionImpl;
import me.hfox.craftbot.entity.data.Hand;
import me.hfox.craftbot.exception.connection.BotEncryptionException;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.protocol.ClientPacket;
import me.hfox.craftbot.protocol.Protocol;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.handshake.client.ProtocolState;
import me.hfox.craftbot.protocol.login.client.PacketClientLoginEncryptionResponse;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginDisconnect;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginEncryptionRequest;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginSetCompression;
import me.hfox.craftbot.protocol.login.server.PacketServerLoginSuccess;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayClientSettings;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayClientStatus;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayKeepAlive;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayTeleportConfirm;
import me.hfox.craftbot.protocol.play.client.data.ChatMode;
import me.hfox.craftbot.protocol.play.client.data.ClientStatusAction;
import me.hfox.craftbot.protocol.play.server.*;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusPing;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusPong;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusResponse;
import me.hfox.craftbot.protocol.status.server.data.ServerListPingResponse;
import me.hfox.craftbot.utils.CryptoUtils;
import me.hfox.craftbot.world.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class ServerConnection implements Connection {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConnection.class);

    private final Client client;
    private final SocketChannel channel;
    private final Compression compression;

    private Protocol protocol;

    public ServerConnection(Client client, SocketChannel channel, Protocol protocol) {
        this.client = client;
        this.channel = channel;
        this.compression = new CompressionImpl();
        this.protocol = protocol;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    @Override
    public Compression getCompression() {
        return compression;
    }

    @Override
    public boolean isConnected() {
        return channel.isOpen();
    }

    @Override
    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public void disconnect() {
        channel.close();
    }

    @Override
    public void writePacket(ClientPacket packet) {
        handle(packet);

        if (channel.eventLoop().inEventLoop()) {
            channel.writeAndFlush(packet);
        } else {
            channel.eventLoop().execute(() -> channel.writeAndFlush(packet));
        }
    }

    @Override
    public void handle(ServerPacket packet) {
        LOGGER.debug("Received {}", packet.getClass().getSimpleName());

        /*if (!(packet instanceof PacketServerPlayEntityPosition)
                && !(packet instanceof PacketServerPlayEntityHeadLook)
                && !(packet instanceof PacketServerPlayEntityVelocity)
                && !(packet instanceof PacketServerPlayEntityPositionAndRotation)
                && !(packet instanceof PacketServerPlayEntityRotation)
                && !(packet instanceof PacketServerPlayDestroyEntities)) {
            LOGGER.info("Received {}", packet.getClass().getSimpleName());
        }*/

        try {
            client.onReceive(packet);
        } catch (Throwable ex) {
            LOGGER.error("Unable to pass Packet to client", ex);
        }

        if (packet instanceof PacketServerStatusResponse) {
            ServerListPingResponse response = ((PacketServerStatusResponse) packet).getResponse();
            LOGGER.info("Protocol '{}' v{}", response.getVersion().getName(), response.getVersion().getProtocol());
            LOGGER.info("Players {}/{}", response.getPlayers().getOnline(), response.getPlayers().getMax());
            LOGGER.info("MOTD: '{}'", response.getDescription());

            writePacket(new PacketClientStatusPing(System.currentTimeMillis()));
        } else if (packet instanceof PacketServerStatusPong) {
            long payload = ((PacketServerStatusPong) packet).getPayload();
            LOGGER.info("Response time: {}ms", (System.currentTimeMillis() - payload));
            disconnect();
        } else if (packet instanceof PacketServerLoginEncryptionRequest) {
            PacketServerLoginEncryptionRequest request = (PacketServerLoginEncryptionRequest) packet;

            LOGGER.info("Encryption requested!");

            try {
                SecretKey secretKey = CryptoUtils.generateSecretKey();
                LOGGER.info("Generated secret key");
                PublicKey publicKey = CryptoUtils.decodePublicKey(request.getPublicKey());
                LOGGER.info("Decoded public key");

                String hash = CryptoUtils.getServerIdHash(request.getServerId(), publicKey, secretKey);
                LOGGER.info("Generated server id hash: {}", hash);

                client.getSession().joinServer(hash);
                LOGGER.info("Joined server!");

                writePacket(new PacketClientLoginEncryptionResponse(secretKey.getEncoded(), request.getVerifyToken()));
            } catch (BotEncryptionException ex) {
                LOGGER.error("Unable to encrypt", ex);
                disconnect();
            } catch (BotAuthenticationFailedException ex) {
                LOGGER.error("Unable to authenticate", ex);
                disconnect();
            } catch (Exception ex) {
                LOGGER.error("Unexpected scenario", ex);
                disconnect();
            }
        } else if (packet instanceof PacketServerLoginSuccess) {
            LOGGER.info("Login success!");
            protocol.setState(ProtocolState.PLAY);

            try {
                client.completeLogin();
            } catch (Throwable ex) {
                LOGGER.error("Unable to complete login", ex);
            }

        } else if (packet instanceof PacketServerLoginSetCompression) {
            int threshold = ((PacketServerLoginSetCompression) packet).getThreshold();
            getCompression().enable(threshold);
            LOGGER.info("Read compression threshold as {}", threshold);
        } else if (packet instanceof PacketServerLoginDisconnect) {
            ChatComponent reason = ((PacketServerLoginDisconnect) packet).getReason();
            LOGGER.info("Disconnected: '{}'", reason);
            disconnect();
        } else if (packet instanceof PacketServerPlayKeepAlive) {
            writePacket(new PacketClientPlayKeepAlive(((PacketServerPlayKeepAlive) packet).getKeepAliveId()));
        } else if (packet instanceof PacketServerPlayJoinGame) {
            PacketServerPlayJoinGame joinGame = (PacketServerPlayJoinGame) packet;
            LOGGER.info("Joining game...");
            LOGGER.info("  - Entity ID      : {}", joinGame.getEntityId());
            LOGGER.info("  - Gamemode       : {}", joinGame.getGamemode());
            LOGGER.info("  - Dimension      : {}", joinGame.getDimension());
            LOGGER.info("  - Seed           : {}", joinGame.getSeed());
            LOGGER.info("  - Max player     : {}", joinGame.getMaxPlayers());
            LOGGER.info("  - Level type     : {}", joinGame.getLevelType());
            LOGGER.info("  - View distance  : {}", joinGame.getViewDistance());
            LOGGER.info("  - Reduced debug  : {}", joinGame.isReducedDebug());
            LOGGER.info("  - Respawn screen : {}", joinGame.isRespawnScreen());
        } else if (packet instanceof PacketServerPlayChatMessage) {
            PacketServerPlayChatMessage chatMessage = (PacketServerPlayChatMessage) packet;
            LOGGER.info("[{}]: {}", chatMessage.getPosition(), chatMessage.getChat());
        } else if (packet instanceof PacketServerPlayPluginMessage) {
            PacketServerPlayPluginMessage pluginMessage = (PacketServerPlayPluginMessage) packet;
            LOGGER.info("[PLUGIN] [{}]: {}", pluginMessage.getChannel(), new String(pluginMessage.getData()));
        } else if (packet instanceof PacketServerPlayServerDifficulty) {
            PacketServerPlayServerDifficulty serverDifficulty = (PacketServerPlayServerDifficulty) packet;
            LOGGER.info("Server difficulty: {} (locked: {})", serverDifficulty.getDifficulty(), serverDifficulty.isDifficultyLocked());
        } else if (packet instanceof PacketServerPlayPlayerAbilities) {
            PacketServerPlayPlayerAbilities playerAbilities = (PacketServerPlayPlayerAbilities) packet;
            LOGGER.info("Player abilities:");
            LOGGER.info("  - FOV: {}", playerAbilities.getFieldOfViewModifier());
            LOGGER.info("  - Flight speed: {}", playerAbilities.getFlyingSpeed());
            LOGGER.info("    - Invulnerable : {}", playerAbilities.getAbilities().isInvulnerable());
            LOGGER.info("    - Flying       : {}", playerAbilities.getAbilities().isFlying());
            LOGGER.info("    - Allow flying : {}", playerAbilities.getAbilities().isAllowFlying());
            LOGGER.info("    - Instant break: {}", playerAbilities.getAbilities().isInstantBreak());
        } else if (packet instanceof PacketServerPlayHeldItemChange) {
            PacketServerPlayHeldItemChange heldItemChange = (PacketServerPlayHeldItemChange) packet;
            LOGGER.info("Held item: {}", heldItemChange.getSlot());
        } else if (packet instanceof PacketServerPlayPlayerPositionAndLook) {
            PacketServerPlayPlayerPositionAndLook positionAndLook = (PacketServerPlayPlayerPositionAndLook) packet;
            writePacket(new PacketClientPlayTeleportConfirm(positionAndLook.getTeleportId()));
        } else if (packet instanceof PacketServerPlayDisconnect) {
            PacketServerPlayDisconnect disconnect = (PacketServerPlayDisconnect) packet;
            LOGGER.info("Disconnected by server: {}", disconnect.getReason());
        } else if (packet instanceof PacketServerPlayChangeGameState) {
            LOGGER.info("Told to change game state: {}", packet);
        }
    }

    @Override
    public void handle(ClientPacket packet) {
        if (packet instanceof PacketClientHandshake) {
            protocol.setState(((PacketClientHandshake) packet).getNextState());
        }

        client.onSend(packet);
    }

}
