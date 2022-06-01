package com.example.sameapp;

public class Message {

    private int id;
    private String created;
    private boolean sent;
    private String UserId;
    private String content;
    private String contactId;
    // CONTACT


    public Message(){

    }
    public Message(int id, String created, boolean sent, String userId, String content, String contactId) {
        this.id = id;
        this.created = created;
        this.sent = sent;
        UserId = userId;
        this.content = content;
        this.contactId = contactId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }
}
