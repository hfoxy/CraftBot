package me.hfox.craftbot.connection.client.session;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import me.hfox.craftbot.connection.client.session.dto.request.AuthRefreshDto;
import me.hfox.craftbot.connection.client.session.dto.request.AuthValidateDto;
import me.hfox.craftbot.connection.client.session.dto.request.AuthenticationAgentDto;
import me.hfox.craftbot.connection.client.session.dto.request.AuthenticationDto;
import me.hfox.craftbot.connection.client.session.dto.response.AuthSessionResponseDto;
import me.hfox.craftbot.connection.client.session.dto.response.RefreshSessionResponseDto;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.exception.session.BotRefreshTokenFailedException;
import me.hfox.craftbot.utils.StringUtils;

import java.util.UUID;

public class YggdrasilSession implements Session {

    private static final String TOKEN_CHARS = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final String username;
    private final String password;

    private String accessToken;
    private String clientToken;
    private String name;
    private UUID uniqueId;

    public YggdrasilSession(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private boolean hasAuthenticatedBefore() {
        return !(name == null || uniqueId == null || accessToken == null || clientToken == null);
    }

    @Override
    public boolean isOnline() {
        if (!hasAuthenticatedBefore()) {
            return false;
        }

        AuthValidateDto validateDto = new AuthValidateDto(accessToken, clientToken);
        int validationResult = Unirest.post("https://authserver.mojang.com/validate")
                .body(validateDto).header("Content-Type", "application/json").asEmpty().getStatus();

        return validationResult == 200 || validationResult == 204;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    private void performAuthentication() throws BotAuthenticationFailedException {
        clientToken = StringUtils.generateRandomString(TOKEN_CHARS, 32);

        AuthenticationDto authDto = new AuthenticationDto(
                new AuthenticationAgentDto("Minecraft", 1),
                username, password, clientToken, false
        );

        HttpResponse<AuthSessionResponseDto> response = Unirest.post("https://authserver.mojang.com/authenticate")
                .body(authDto).header("Content-Type", "application/json").asObject(AuthSessionResponseDto.class);

        if (response.getStatus() != 200) {
            throw new BotAuthenticationFailedException("Unable to authenticate: Status " + response.getStatus() + " - " + response.getStatusText());
        }

        AuthSessionResponseDto sessionDto = response.getBody();
        if (sessionDto == null) {
            throw new BotAuthenticationFailedException("Unable to parse response body");
        }

        accessToken = sessionDto.getAccessToken();
        name = sessionDto.getSelectedProfile().getName();
        uniqueId = StringUtils.toUuid(sessionDto.getSelectedProfile().getId());
    }

    private void refreshToken() throws BotRefreshTokenFailedException {
        if (!hasAuthenticatedBefore()) {
            throw new BotRefreshTokenFailedException("Session has never been authenticated");
        }

        AuthRefreshDto refreshRequestDto = new AuthRefreshDto(accessToken, clientToken, false);

        HttpResponse<RefreshSessionResponseDto> response = Unirest.post("https://authserver.mojang.com/refresh")
                .body(refreshRequestDto).header("Content-Type", "application/json").asObject(AuthSessionResponseDto.class);

        if (response.getStatus() != 200) {
            throw new BotRefreshTokenFailedException("Unable to refresh token");
        }

        RefreshSessionResponseDto refreshDto = response.getBody();
        if (refreshDto == null) {
            throw new BotRefreshTokenFailedException("Unable to parse response body");
        }

        accessToken = refreshDto.getAccessToken();
        name = refreshDto.getSelectedProfile().getName();
        uniqueId = StringUtils.toUuid(refreshDto.getSelectedProfile().getId());
    }

    @Override
    public void authenticate() throws BotAuthenticationFailedException {
        if (isOnline()) {
            return;
        }

        try {
            refreshToken();
            return;
        } catch (BotRefreshTokenFailedException ex) {
            // unable to refresh token, attempting to authenticate again
        }

        performAuthentication();
    }

    @Override
    public void joinServer(String serverHash) throws BotAuthenticationFailedException {
        authenticate();

        AuthValidateDto validateDto = new AuthValidateDto(accessToken, clientToken);
        int validationResult = Unirest.post("https://authserver.mojang.com/validate")
                .body(validateDto).header("Content-Type", "application/json").asEmpty().getStatus();

        if (validationResult != 204 && validationResult != 200) {
            throw new BotAuthenticationFailedException("Unable to authenticate with session server: " + validationResult);
        }
    }

}
