package me.hfox.craftbot;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

public interface CraftBot {

    String getName();

    String getVersion();

    Set<Integer> getSupportedProtocols();

    ObjectMapper getMapper();

}
