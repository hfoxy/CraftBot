package me.hfox.craftbot.connection.client.session.dto.request;

public class AuthValidateDto {

    private String accessToken;
    private String clientToken;

    public AuthValidateDto() {
        // jackson
    }

    public AuthValidateDto(String accessToken, String clientToken) {
        this.accessToken = accessToken;
        this.clientToken = clientToken;
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

}
