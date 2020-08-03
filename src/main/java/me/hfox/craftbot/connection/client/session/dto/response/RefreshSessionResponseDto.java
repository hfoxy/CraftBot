package me.hfox.craftbot.connection.client.session.dto.response;

public class RefreshSessionResponseDto {

    private AuthSessionUserDto user;
    private String accessToken;
    private String clientToken;
    private AuthSessionProfileDto selectedProfile;

    public AuthSessionUserDto getUser() {
        return user;
    }

    public void setUser(AuthSessionUserDto user) {
        this.user = user;
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

    public AuthSessionProfileDto getSelectedProfile() {
        return selectedProfile;
    }

    public void setSelectedProfile(AuthSessionProfileDto selectedProfile) {
        this.selectedProfile = selectedProfile;
    }

}
