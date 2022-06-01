package com.example.sameapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactAppDB extends RoomDatabase {
    public abstract ContactDao contactDao();
}
