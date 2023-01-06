package com.example.model;

public class Chat {

    private String chatId;
    private String chatContent;
    private String chatProfilePicture;
    private String chatUsername;

    public Chat() {

    }

    public Chat(String chatId, String chatContent) {
        this.chatId = chatId;
        this.chatContent = chatContent;
    }

    public Chat(String chatId, String chatContent, String chatProfilePicture, String chatUsername) {
        this.chatContent = chatContent;
        this.chatId = chatId;
        this.chatProfilePicture = chatProfilePicture;
        this.chatUsername = chatUsername;
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
}
