package me.hfox.craftbot.protocol.status.server;

import me.hfox.craftbot.protocol.ServerPacket;
import me.hfox.craftbot.protocol.stream.ProtocolBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PacketServerStatusResponse implements ServerPacket {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketServerStatusResponse.class);

    private String response;

    /**
     * JSON response @see <a href="https://wiki.vg/Server_List_Ping#Response">wiki.vg</a>
     */
    public String getResponse() {
        return response;
    }

    @Override
    public void read(ProtocolBuffer buffer) {
        response = buffer.readString();
    }

}
