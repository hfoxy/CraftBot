package me.hfox.craftbot.protocol.pipeline;

import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import me.hfox.craftbot.connection.Connection;
import me.hfox.craftbot.connection.ServerConnection;
import me.hfox.craftbot.exception.BotProtocolException;
import me.hfox.craftbot.protocol.JavaProtocol;

public class ProtocolClientHandler extends ChannelInitializer<SocketChannel> {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        try {
            channel.config().setOption(ChannelOption.TCP_NODELAY, true);
        } catch (ChannelException ex) {
            throw new BotProtocolException("Unable to set TCP_NODELAY");
        }

        connection = new ServerConnection(channel, new JavaProtocol());

        channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30));
        channel.pipeline().addLast("splitter", new PacketSplitter());
        channel.pipeline().addLast("inflater", new PacketInflater(connection));
        channel.pipeline().addLast("decoder", new PacketDecoder(connection));
        channel.pipeline().addLast("connection", new ConnectionPipe(connection));

        // encode
        // compress
        // encrypt

        // decrypt
        // decompress
        // decode

        channel.pipeline().addLast("deflator", new PacketDeflator(connection));
        channel.pipeline().addLast("prepender", new PacketPrepender());
        channel.pipeline().addLast("encoder", new PacketEncoder(connection));
    }

}
