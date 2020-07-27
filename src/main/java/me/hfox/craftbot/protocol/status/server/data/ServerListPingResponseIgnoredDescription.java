package me.hfox.craftbot.protocol.status.server.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.hfox.craftbot.chat.ChatComponent;

public class ServerListPingResponseIgnoredDescription extends ServerListPingResponse {

    @Override
    @JsonIgnore
    public ChatComponent getDescription() {
        return super.getDescription();
    }

    @Override
    @JsonIgnore
    public void setDescription(ChatComponent description) {
        super.setDescription(description);
    }

}
