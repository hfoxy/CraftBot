package me.hfox.craftbot.connection.client.session.dto.request;

public class AuthenticationDto {

    private AuthenticationAgentDto agent;
    private String username;
    private String password;
    private String clientToken;
    private boolean requestUser;

    public AuthenticationDto() {
        // jackson
    }

    public AuthenticationDto(AuthenticationAgentDto agent, String username, String password, String clientToken, boolean requestUser) {
        this.agent = agent;
        this.username = username;
        this.password = password;
        this.clientToken = clientToken;
        this.requestUser = requestUser;
    }

    public AuthenticationAgentDto getAgent() {
        return agent;
    }

    public void setAgent(AuthenticationAgentDto agent) {
        this.agent = agent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public boolean isRequestUser() {
        return requestUser;
    }

    public void setRequestUser(boolean requestUser) {
        this.requestUser = requestUser;
    }

}
