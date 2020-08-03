package me.hfox.craftbot.connection.client.session.dto.request;

public class AuthenticationAgentDto {

    private String name;
    private int version;

    public AuthenticationAgentDto() {
        // jackson
    }

    public AuthenticationAgentDto(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
