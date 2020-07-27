package me.hfox.craftbot;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface CraftBot {

    String getName();

    String getVersion();

    ObjectMapper getMapper();

}
