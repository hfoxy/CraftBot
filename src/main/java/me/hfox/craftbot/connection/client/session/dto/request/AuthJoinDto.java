package me.hfox.craftbot.connection.client.session.dto.request;

public class AuthJoinDto {

    private String accessToken;
    private String selectedProfile;
    private String serverId;

    public AuthJoinDto(String accessToken, String selectedProfile, String serverId) {
        this.accessToken = accessToken;
        this.selectedProfile = selectedProfile;
        this.serverId = serverId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getSelectedProfile() {
        return selectedProfile;
    }

    public String getServerId() {
        return serverId;
    }

}
