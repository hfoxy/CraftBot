package me.hfox.craftbot.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClickEventComponent {

    @JsonProperty("open_url")
    private String openUrl;

    @Deprecated
    @JsonProperty("open_file")
    private String openFile;

    @JsonProperty("run_command")
    private String runCommand;

    @Deprecated
    @JsonProperty("twitch_user_info")
    private String twitchUserInfo;

    @JsonProperty("suggest_command")
    private String suggestCommand;

    @JsonProperty("change_page")
    private String changePage;

    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    @Deprecated
    public String getOpenFile() {
        return openFile;
    }

    @Deprecated
    public void setOpenFile(String openFile) {
        this.openFile = openFile;
    }

    public String getRunCommand() {
        return runCommand;
    }

    public void setRunCommand(String runCommand) {
        this.runCommand = runCommand;
    }

    @Deprecated
    public String getTwitchUserInfo() {
        return twitchUserInfo;
    }

    @Deprecated
    public void setTwitchUserInfo(String twitchUserInfo) {
        this.twitchUserInfo = twitchUserInfo;
    }

    public String getSuggestCommand() {
        return suggestCommand;
    }

    public void setSuggestCommand(String suggestCommand) {
        this.suggestCommand = suggestCommand;
    }

    public String getChangePage() {
        return changePage;
    }

    public void setChangePage(String changePage) {
        this.changePage = changePage;
    }

}
