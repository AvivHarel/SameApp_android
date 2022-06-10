package com.example.sameapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Contact {
    @PrimaryKey(autoGenerate=false)
    @NonNull
    // his User Name.
    private String contactID;
    //private String userNameOwner;
    private int pictureId;
    private String lastMassage;
    private String lastMassageSendingTime;



    //list of messages
    // item of User.

    public Contact(){

    }

    public Contact(String contactID, int pictureId, String lastMassage, String lastMassageSendingTime) {
        this.contactID = contactID;
        this.pictureId = pictureId;
        this.lastMassage = lastMassage;
        this.lastMassageSendingTime = lastMassageSendingTime;
    }

    public int getPictureId() {
        return pictureId;
    }

    public String getLastMassage() {
        return lastMassage;
    }

    public String getLastMassageSendingTime() {
        return lastMassageSendingTime;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public void setLastMassage(String lastMassage) {
        this.lastMassage = lastMassage;
    }

    public void setLastMassageSendingTime(String lastMassageSendingTime) {
        this.lastMassageSendingTime = lastMassageSendingTime;
    }
}