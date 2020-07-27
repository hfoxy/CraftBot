package me.hfox.craftbot.protocol.status.server.data;

import java.util.List;

public class ServerListPingResponsePlayers {

    private int max;
    private int online;
    private List<ServerListPingResponsePlayerSample> sample;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public List<ServerListPingResponsePlayerSample> getSample() {
        return sample;
    }

    public void setSample(List<ServerListPingResponsePlayerSample> sample) {
        this.sample = sample;
    }

}
