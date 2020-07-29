package me.hfox.craftbot.protocol.status.server;

import me.hfox.craftbot.Bot;
import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.status.server.data.ServerListPingResponse;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PacketServerStatusResponse implements ServerPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketServerStatusResponse.class);

    private String responseString;
    private ServerListPingResponse response;

    /**
     * JSON response @see <a href="https://wiki.vg/Server_List_Ping#Response">wiki.vg</a>
     */
    public String getResponseString() {
        return responseString;
    }

    public ServerListPingResponse getResponse() {
        return response;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        responseString = buffer.readString();

        try {
            response = Bot.getBot().getMapper().readValue(responseString, ServerListPingResponse.class);
        } catch (IOException ex) {
            LOGGER.error("Unable to process response json", ex);
        }
    }

}
