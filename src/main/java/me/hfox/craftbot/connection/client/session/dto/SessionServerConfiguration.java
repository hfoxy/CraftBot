package me.hfox.craftbot.connection.client.session.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionServerConfiguration {

    // "https://authserver.mojang.com/validate"
    private final String validateUrl;

    // "https://authserver.mojang.com/authenticate"
    private final String authenticateUrl;

    // "https://authserver.mojang.com/refresh"
    private final String refreshUrl;

    // "https://sessionserver.mojang.com/session/minecraft/join"
    private final String joinUrl;

    private int checkIntervalMillis = 360000; // 6 minutes
    private int accountsPerCheck = 5;

    @JsonCreator
    public SessionServerConfiguration(
            @JsonProperty("validateUrl") String validateUrl,
            @JsonProperty("authenticateUrl") String authenticateUrl,
            @JsonProperty("refreshUrl") String refreshUrl,
            @JsonProperty("joinUrl") String joinUrl
    ) {
        this.validateUrl = validateUrl;
        this.authenticateUrl = authenticateUrl;
        this.refreshUrl = refreshUrl;
        this.joinUrl = joinUrl;
    }

    public String getValidateUrl() {
        return validateUrl;
    }

    public String getAuthenticateUrl() {
        return authenticateUrl;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public int getCheckIntervalMillis() {
        return checkIntervalMillis;
    }

    public void setCheckIntervalMillis(int checkIntervalMillis) {
        this.checkIntervalMillis = checkIntervalMillis;
    }

    public int getAccountsPerCheck() {
        return accountsPerCheck;
    }

    public void setAccountsPerCheck(int accountsPerCheck) {
        this.accountsPerCheck = accountsPerCheck;
    }

}
