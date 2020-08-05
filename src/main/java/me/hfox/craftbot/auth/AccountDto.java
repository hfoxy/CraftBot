package me.hfox.craftbot.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;
import java.util.UUID;

import static me.hfox.craftbot.connection.client.session.YggdrasilSession.SESSION_TIMEOUT;

public class AccountDto {

    private String username;
    private String password;
    private boolean invalidDetails;

    private String accessToken;
    private String clientToken;
    private String name;
    private String uuid;

    private boolean authenticated;
    private long lastAuthenticated;

    @JsonIgnore
    private UUID uniqueId;

    @JsonIgnore
    public boolean hasBeenAuthenticated() {
        return username != null && password != null && accessToken != null && clientToken != null && name != null && uuid != null && authenticated && (System.currentTimeMillis() - lastAuthenticated) < SESSION_TIMEOUT;
    }

    public boolean isInvalidDetails() {
        return invalidDetails;
    }

    public void setInvalidDetails(boolean invalidDetails) {
        this.invalidDetails = invalidDetails;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
        this.uniqueId = null;
    }

    public UUID getUniqueId() {
        if (uniqueId != null) {
            return uniqueId;
        }

        if (uuid != null) {
            uniqueId = UUID.fromString(uuid);
        }

        return uniqueId;
    }

    public void setUniqueId(UUID uniqueId) {
        this.uuid = uniqueId.toString();
        this.uniqueId = uniqueId;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public long getLastAuthenticated() {
        return lastAuthenticated;
    }

    public void setLastAuthenticated(long lastAuthenticated) {
        this.lastAuthenticated = lastAuthenticated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return username.equals(that.username) &&
                password.equals(that.password) &&
                Objects.equals(accessToken, that.accessToken) &&
                Objects.equals(clientToken, that.clientToken) &&
                Objects.equals(name, that.name) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(uniqueId, that.uniqueId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, accessToken, clientToken, name, uuid, uniqueId);
    }

}
