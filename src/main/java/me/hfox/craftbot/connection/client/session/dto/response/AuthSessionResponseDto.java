package me.hfox.craftbot.connection.client.session.dto.response;

import java.util.List;

public class AuthSessionResponseDto extends RefreshSessionResponseDto {

    private List<AuthSessionProfileDto> availableProfiles;

    public List<AuthSessionProfileDto> getAvailableProfiles() {
        return availableProfiles;
    }

    public void setAvailableProfiles(List<AuthSessionProfileDto> availableProfiles) {
        this.availableProfiles = availableProfiles;
    }

}
