package me.hfox.craftbot.connection.client.session.dto.request;

public class AuthRefreshDto {

    private String accessToken;
    private String clientToken;
    private boolean requestUser;

    public AuthRefreshDto() {
        // jackson
    }

    public AuthRefreshDto(String accessToken, String clientToken, boolean requestUser) {
        this.accessToken = accessToken;
        this.clientToken = clientToken;
        this.requestUser = requestUser;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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
