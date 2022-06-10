package com.example.sameapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sameapp.dao.ContactDao;
import com.example.sameapp.dao.MessageDao;

@Database(entities = {Contact.class, Message.class},version = 2)
public abstract class ContactAppDB extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract MessageDao messageDao();

}
