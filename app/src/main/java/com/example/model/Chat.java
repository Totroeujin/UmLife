package com.example.model;

public class Chat {

    private String chatId;
    private String chatContent;
    private String chatProfilePicture;
    private String chatUsername;
    private String utcTime;

    public Chat() {

    }

    public Chat(String chatUsername, String chatContent, String utcTime, String chatProfilePicture) {
        this.chatUsername = chatUsername;
        this.chatContent = chatContent;
        this.utcTime = utcTime;
        this.chatProfilePicture = chatProfilePicture;
    }

    public Chat(String chatId, String chatContent, String chatProfilePicture, String chatUsername, String utcTime) {
        this.chatContent = chatContent;
        this.chatId = chatId;
        this.chatProfilePicture = chatProfilePicture;
        this.chatUsername = chatUsername;
        this.utcTime = utcTime;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatDetail() {
        return chatContent;
    }

    public void setChatDetail(String chatContent) {
        this.chatContent = chatContent;
    }

    public String getChatProfileImage() {
        return chatProfilePicture;
    }

    public void setChatProfileImage(String chatProfilePicture) {
        this.chatProfilePicture = chatProfilePicture;
    }

    public String getChatUsername() {
        return chatUsername;
    }

    public void setChatUsername(String chatUsername) {
        this.chatUsername = chatUsername;
    }

    public String getUtcTime() {
        return utcTime;
    }

    public void setUtcTime(String utcTime) {
        this.utcTime = utcTime;
    }
}
