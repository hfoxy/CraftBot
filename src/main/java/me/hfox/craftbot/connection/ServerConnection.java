package me.hfox.craftbot.connection;

import io.netty.channel.socket.SocketChannel;
import me.hfox.craftbot.connection.compression.Compression;
import me.hfox.craftbot.connection.compression.CompressionImpl;
import me.hfox.craftbot.protocol.*;
import me.hfox.craftbot.protocol.handshake.client.PacketClientHandshake;
import me.hfox.craftbot.protocol.status.client.PacketClientStatusPing;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusPong;
import me.hfox.craftbot.protocol.status.server.PacketServerStatusResponse;
import me.hfox.craftbot.protocol.status.server.data.ServerListPingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerConnection implements Connection {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerConnection.class);

    private final SocketChannel channel;
    private final Compression compression;

    private JavaProtocol protocol;

    public ServerConnection(SocketChannel channel, JavaProtocol protocol) {
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
        }
    }

    @Override
    public void handle(ClientPacket packet) {
        if (packet instanceof PacketClientHandshake) {
            protocol.setState(((PacketClientHandshake) packet).getNextState());
        }
    }

}
