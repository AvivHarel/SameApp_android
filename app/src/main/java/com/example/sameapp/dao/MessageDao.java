package com.example.sameapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sameapp.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message")
    List<Message> index();

    @Query("SELECT * FROM message WHERE contactID = :contactId")
    List<Message> get(String contactId);

    @Insert
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);
}
