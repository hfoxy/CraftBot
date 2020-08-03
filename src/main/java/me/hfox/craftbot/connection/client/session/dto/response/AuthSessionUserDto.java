package me.hfox.craftbot.connection.client.session.dto.response;

import java.util.List;

public class AuthSessionUserDto {

    private String id;
    private String username;
    private List<AuthSessionUserPropertyDto> properties;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<AuthSessionUserPropertyDto> getProperties() {
        return properties;
    }

    public void setProperties(List<AuthSessionUserPropertyDto> properties) {
        this.properties = properties;
    }

}
