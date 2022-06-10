package com.example.sameapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.sameapp.Contact;

import java.util.List;
import java.util.Map;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact")
    List<Contact> index();

    @Query("SELECT * FROM contact WHERE contactID = :contactId")
    Contact get(String contactId);

    @Insert
    void insert(Contact... contacts);

    @Update
    void update(Contact... contacts);

    @Delete
    void delete(Contact... contacts);

}
