package me.hfox.craftbot.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HoverEventComponent {

    @JsonProperty("show_text")
    private StringSupportedChatComponent showText;

    @JsonProperty("show_item")
    private String showItem;

    @JsonProperty("show_entity")
    private String showEntity;

    @Deprecated
    @JsonProperty("show_achievement")
    private String showAchievement;

    public StringSupportedChatComponent getShowText() {
        return showText;
    }

    public void setShowText(StringSupportedChatComponent showText) {
        this.showText = showText;
    }

    public String getShowItem() {
        return showItem;
    }

    public void setShowItem(String showItem) {
        this.showItem = showItem;
    }

    public String getShowEntity() {
        return showEntity;
    }

    public void setShowEntity(String showEntity) {
        this.showEntity = showEntity;
    }

    @Deprecated
    public String getShowAchievement() {
        return showAchievement;
    }

    @Deprecated
    public void setShowAchievement(String showAchievement) {
        this.showAchievement = showAchievement;
    }

}
