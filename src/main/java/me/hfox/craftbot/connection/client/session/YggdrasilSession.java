package me.hfox.craftbot.connection.client.session;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import me.hfox.craftbot.Bot;
import me.hfox.craftbot.auth.AccountDto;
import me.hfox.craftbot.connection.client.session.dto.request.*;
import me.hfox.craftbot.connection.client.session.dto.response.AuthErrorResponse;
import me.hfox.craftbot.connection.client.session.dto.response.AuthSessionResponseDto;
import me.hfox.craftbot.connection.client.session.dto.response.RefreshSessionResponseDto;
import me.hfox.craftbot.exception.session.BotAuthenticationFailedException;
import me.hfox.craftbot.exception.session.BotRefreshTokenFailedException;
import me.hfox.craftbot.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.function.Function;

public class YggdrasilSession implements Session {

    public static final long SESSION_TIMEOUT = 4 * 60 * 60 * 1000; // 4 hours

    private static final Logger LOGGER = LoggerFactory.getLogger(YggdrasilSession.class);

    private static final String TOKEN_CHARS = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final AccountDto accountDto;
    private Function<AccountDto, Void> updateAction;

    public YggdrasilSession(String username, String password) {
        this.accountDto = new AccountDto();
        this.accountDto.setUsername(username);
        this.accountDto.setPassword(password);
    }

    public YggdrasilSession(AccountDto accountDto) {
        this(accountDto, null);
    }

    public YggdrasilSession(AccountDto accountDto, Function<AccountDto, Void> updateAction) {
        this.accountDto = accountDto;
        this.updateAction = updateAction;
    }

    public Function<AccountDto, ?> getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(Function<AccountDto, Void> updateAction) {
        this.updateAction = updateAction;
    }

    private void persist() {
        if (updateAction != null) {
            updateAction.apply(accountDto);
        }
    }

    public String getUsername() {
        return accountDto.getUsername();
    }

    public String getPassword() {
        return accountDto.getPassword();
    }

    public String getAccessToken() {
        return accountDto.getAccessToken();
    }

    private void setAccessToken(String accessToken) {
        this.accountDto.setAccessToken(accessToken);
    }

    public String getClientToken() {
        return accountDto.getClientToken();
    }

    private void setClientToken(String clientToken) {
        this.accountDto.setClientToken(clientToken);
    }

    private boolean hasAuthenticationDetails() {
        return getName() != null && getUniqueId() != null && getAccessToken() != null && getClientToken() != null;
    }

    private boolean isAuthenticationCached() {
        if (!hasAuthenticationDetails()) {
            return false;
        }

        // LOGGER.info("{} && ({} - {}) < {}: {}", isAuthenticated(), System.currentTimeMillis(), getLastAuthenticated(), SESSION_TIMEOUT, result);
        return isAuthenticated() && (System.currentTimeMillis() - getLastAuthenticated()) < SESSION_TIMEOUT;
    }

    @Override
    public boolean isOnline() {
        if (!hasAuthenticationDetails()) {
            return false;
        }

        if (isAuthenticationCached()) {
            return true;
        }

        AuthValidateDto validateDto = new AuthValidateDto(getAccessToken(), getClientToken());
        int validationResult = Unirest.post(Bot.getSessionConfiguration().getValidateUrl())
                .body(validateDto).header("Content-Type", "application/json").asEmpty().getStatus();

        boolean result = validationResult == 200 || validationResult == 204;
        if (result) {
            setLastAuthenticated(System.currentTimeMillis());
        }

        setAuthenticated(result);
        persist();
        return result;
    }

    @Override
    public String getName() {
        return accountDto.getName();
    }

    private void setName(String name) {
        this.accountDto.setName(name);
    }

    @Override
    public UUID getUniqueId() {
        return accountDto.getUniqueId();
    }

    private void setUniqueId(UUID uniqueId) {
        this.accountDto.setUniqueId(uniqueId);
    }

    public boolean isAuthenticated() {
        return accountDto.isAuthenticated();
    }

    private void setAuthenticated(boolean authenticated) {
        this.accountDto.setAuthenticated(authenticated);
    }

    public long getLastAuthenticated() {
        return accountDto.getLastAuthenticated();
    }

    private void setLastAuthenticated(long lastAuthenticated) {
        this.accountDto.setLastAuthenticated(lastAuthenticated);
    }

    private void performAuthentication() throws BotAuthenticationFailedException {
        setClientToken(StringUtils.generateRandomString(TOKEN_CHARS, 32));

        AuthenticationDto authDto = new AuthenticationDto(
                new AuthenticationAgentDto("Minecraft", 1),
                getUsername(), getPassword(), getClientToken(), false
        );

        HttpResponse<AuthSessionResponseDto> response = Unirest.post(Bot.getSessionConfiguration().getAuthenticateUrl())
                .body(authDto).header("Content-Type", "application/json").asObject(AuthSessionResponseDto.class);

        if (response.getStatus() != 200) {
            AuthErrorResponse errorResponse = response.mapError(AuthErrorResponse.class);
            String invalidCreds = "";

            if (errorResponse != null) {
                LOGGER.debug("Error! {}", errorResponse.getErrorMessage());
                if (errorResponse.getErrorMessage() != null && errorResponse.getErrorMessage().toLowerCase().contains("invalid credentials")) {
                    invalidCreds = ". Invalid credentials";
                    accountDto.setInvalidDetails(true);
                    persist();
                }
            }

            throw new BotAuthenticationFailedException("Unable to authenticate: Status " + response.getStatus() + " - " + response.getStatusText() + invalidCreds);
        }

        AuthSessionResponseDto sessionDto = response.getBody();
        if (sessionDto == null) {
            throw new BotAuthenticationFailedException("Unable to parse response body");
        }

        setAccessToken(sessionDto.getAccessToken());
        setName(sessionDto.getSelectedProfile().getName());
        setUniqueId(StringUtils.toUuid(sessionDto.getSelectedProfile().getId()));

        setAuthenticated(true);
        setLastAuthenticated(System.currentTimeMillis());
        persist();
    }

    private void refreshToken() throws BotRefreshTokenFailedException {
        if (!hasAuthenticationDetails()) {
            throw new BotRefreshTokenFailedException("Session has never been authenticated");
        }

        AuthRefreshDto refreshRequestDto = new AuthRefreshDto(getAccessToken(), getClientToken(), false);

        HttpResponse<RefreshSessionResponseDto> response = Unirest.post(Bot.getSessionConfiguration().getRefreshUrl())
                .body(refreshRequestDto).header("Content-Type", "application/json").asObject(AuthSessionResponseDto.class);

        if (response.getStatus() != 200) {
            throw new BotRefreshTokenFailedException("Unable to refresh token");
        }

        RefreshSessionResponseDto refreshDto = response.getBody();
        if (refreshDto == null) {
            throw new BotRefreshTokenFailedException("Unable to parse response body");
        }

        setAccessToken(refreshDto.getAccessToken());
        setName(refreshDto.getSelectedProfile().getName());
        setUniqueId(StringUtils.toUuid(refreshDto.getSelectedProfile().getId()));

        setAuthenticated(true);
        setLastAuthenticated(System.currentTimeMillis());
        persist();
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

        AuthValidateDto validateDto = new AuthValidateDto(getAccessToken(), getClientToken());
        int validationResult = Unirest.post(Bot.getSessionConfiguration().getValidateUrl())
                .body(validateDto).header("Content-Type", "application/json").asEmpty().getStatus();

        if (validationResult != 204 && validationResult != 200) {
            // throw new BotAuthenticationFailedException("Unable to validate with session server: " + validationResult);
            setAuthenticated(false);
            setLastAuthenticated(0);
            authenticate();
        }

        String uuid = getUniqueId().toString().replace("-", "");
        LOGGER.info("Access Token: {}", getAccessToken());
        LOGGER.info("UUID: {}", uuid);
        LOGGER.info("Server ID: {}", serverHash);
        AuthJoinDto joinDto = new AuthJoinDto(
                getAccessToken(), uuid, serverHash
        );

        int joinResult = Unirest.post(Bot.getSessionConfiguration().getJoinUrl())
                .body(joinDto).header("Content-Type", "application/json").asEmpty().getStatus();


        LOGGER.info("Join result: {}", joinResult);
        if (joinResult != 204 && joinResult != 200) {
            throw new BotAuthenticationFailedException("Unable to authenticate with session server to join: " + validationResult);
        }
    }

}
