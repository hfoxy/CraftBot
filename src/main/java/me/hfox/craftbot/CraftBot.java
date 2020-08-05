package me.hfox.craftbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.hfox.craftbot.connection.client.Client;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface CraftBot {

    String getName();

    String getVersion();

    Set<Integer> getSupportedProtocols();

    ObjectMapper getMapper();

    Set<Client> getClients();

    Optional<Client> findByName(String name);

    Optional<Client> findByUuid(UUID uuid);

}
