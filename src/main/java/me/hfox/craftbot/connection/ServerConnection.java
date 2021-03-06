package me.hfox.craftbot.connection;

import io.netty.channel.socket.SocketChannel;
import me.hfox.craftbot.chat.ChatComponent;
import me.hfox.craftbot.connection.client.Client;
import me.hfox.craftbot.connection.client.ConnectAction;
import me.hfox.craftbot.connection.compression.Compression;
import me.hfox.craftbot.connection.compression.CompressionImpl;
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
import me.hfox.craftbot.protocol.pipeline.PacketEncryptingDecoder;
import me.hfox.craftbot.protocol.pipeline.PacketEncryptingEncoder;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayKeepAlive;
import me.hfox.craftbot.protocol.play.client.PacketClientPlayTeleportConfirm;
import me.hfox.craftbot.protocol.play.server.*;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusPing;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusPong;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusResponse;
import me.hfox.craftbot.protocol.status.server.data.ServerListPingResponse;
import me.hfox.craftbot.utils.CryptoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ServerConnection implements Connection {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConnection.class);

    private final Client client;
    private final SocketChannel channel;
    private final Compression compression;
    private final BlockingQueue<ServerPacket> packets;
    private final Thread packetHandle;
    private final ConnectAction connectAction;

    private boolean firstHealth;
    private boolean firstDisconnect;
    private boolean exitPoll;
    private Protocol protocol;

    public ServerConnection(Client client, SocketChannel channel, Protocol protocol, ConnectAction action) {
        this.client = client;
        this.channel = channel;
        this.compression = new CompressionImpl();
        this.protocol = protocol;
        this.packets = new LinkedBlockingQueue<>();
        this.connectAction = action;
        this.firstHealth = true;
        this.firstDisconnect = true;
        this.packetHandle = new Thread(() -> {
            while (!exitPoll) {
                ServerPacket packet;
                try {
                    packet = packets.poll(25, TimeUnit.SECONDS);
                } catch (InterruptedException ex) {
                    // we have been interrupted
                    return;
                }

                if (packet == null) {
                    continue;
                }

                try {
                    client.onReceive(packet);
                } catch (Throwable ex) {
                    LOGGER.error("Unable to pass Packet to client", ex);
                }
            }
        }, "packet-handle-" + client.getName());

        this.packetHandle.start();
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
        return channel.isActive();
    }

    @Override
    public Protocol getProtocol() {
        return protocol;
    }

    @Override
    public void disconnect() {
        try {
            channel.close();
        } catch (Exception ex) {
            // unable close
        }

        exitPoll = true;

        if (firstDisconnect) {
            firstDisconnect = false;
            if (connectAction != null) {
                connectAction.onConnect(false);
            }
        }
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
        // LOGGER.info("Received {}", packet.getClass().getSimpleName());

        /*if (!(packet instanceof PacketServerPlayEntityPosition)
                && !(packet instanceof PacketServerPlayEntityHeadLook)
                && !(packet instanceof PacketServerPlayEntityVelocity)
                && !(packet instanceof PacketServerPlayEntityPositionAndRotation)
                && !(packet instanceof PacketServerPlayEntityRotation)
                && !(packet instanceof PacketServerPlayDestroyEntities)) {
            LOGGER.info("Received {}", packet.getClass().getSimpleName());
        }*/

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

                String hash = CryptoUtils.getServerIdHash(request.getServerId(), secretKey, publicKey);
                LOGGER.info("Generated server id hash: {}", hash);

                client.getSession().joinServer(hash);
                LOGGER.info("Joined server!");

                // writePacket(new PacketClientLoginEncryptionResponse(
                //         CryptoUtils.encryptData(publicKey, secretKey.getEncoded()),
                //         CryptoUtils.encryptData(publicKey, request.getVerifyToken())
                // ));

                writePacket(new PacketClientLoginEncryptionResponse(
                        CryptoUtils.encryptData(publicKey, secretKey.getEncoded()),
                        CryptoUtils.encryptData(publicKey, request.getVerifyToken())
                ));

                getChannel().pipeline().addBefore("splitter", "decrypt", new PacketEncryptingDecoder(CryptoUtils.createNetworkCipher(2, secretKey)));
                getChannel().pipeline().addBefore("deflator", "encrypt", new PacketEncryptingEncoder(CryptoUtils.createNetworkCipher(1, secretKey)));
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
            protocol.setState(ProtocolState.PLAY);

            try {
                client.completeLogin();
            } catch (Throwable ex) {
                LOGGER.error("Unable to complete login", ex);
            }

            LOGGER.info("Login success!");
        } else if (packet instanceof PacketServerLoginSetCompression) {
            int threshold = ((PacketServerLoginSetCompression) packet).getThreshold();
            getCompression().enable(threshold);
            LOGGER.debug("Read compression threshold as {}", threshold);
        } else if (packet instanceof PacketServerLoginDisconnect) {
            ChatComponent reason = ((PacketServerLoginDisconnect) packet).getReason();
            LOGGER.info("[{}] Disconnected: '{}'", client.getName(), reason);
            disconnect();
        } else if (packet instanceof PacketServerPlayKeepAlive) {
            long keepAliveId = ((PacketServerPlayKeepAlive) packet).getKeepAliveId();
            LOGGER.debug("Received keep alive! {}", keepAliveId);
            writePacket(new PacketClientPlayKeepAlive(keepAliveId));
        } else if (packet instanceof PacketServerPlayUpdateHealth) {
            if (firstHealth) {
                firstHealth = false;
                if (connectAction != null) {
                    connectAction.onConnect(true);
                }
            }
        } else if (packet instanceof PacketServerPlayChatMessage && client.isChatEnabled()) {
            PacketServerPlayChatMessage chatMessage = (PacketServerPlayChatMessage) packet;
            LOGGER.info("[{}]: {}", chatMessage.getPosition(), chatMessage.getChat());
        } else if (packet instanceof PacketServerPlayPluginMessage) {
            PacketServerPlayPluginMessage pluginMessage = (PacketServerPlayPluginMessage) packet;
            LOGGER.debug("[PLUGIN] [{}]: {}", pluginMessage.getChannel(), new String(pluginMessage.getData()));
        } else if (packet instanceof PacketServerPlayPlayerPositionAndLook) {
            PacketServerPlayPlayerPositionAndLook positionAndLook = (PacketServerPlayPlayerPositionAndLook) packet;
            writePacket(new PacketClientPlayTeleportConfirm(positionAndLook.getTeleportId()));
        } else if (packet instanceof PacketServerPlayDisconnect) {
            PacketServerPlayDisconnect disconnect = (PacketServerPlayDisconnect) packet;
            LOGGER.info("Disconnected by server: {}", disconnect.getReason());
        }

        packets.add(packet);
    }

    @Override
    public void handle(ClientPacket packet) {
        if (packet instanceof PacketClientHandshake) {
            protocol.setState(((PacketClientHandshake) packet).getNextState());
        }

        client.onSend(packet);
    }

}
