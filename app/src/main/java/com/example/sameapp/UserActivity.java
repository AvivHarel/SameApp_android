package com.example.sameapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.sameapp.dao.MessageDao;

import java.util.ArrayList;


public class UserActivity extends AppCompatActivity {

    ImageView profilePictureView;
    TextView userNameView;
    Button sendButton;

    private ContactAppDB db;
    private MessageDao messageDao;

    private ArrayList<Message> messages;

    RecyclerView listView;
    MessageListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = Room.databaseBuilder(getApplicationContext(), ContactAppDB.class, "ContactsDB")
                .allowMainThreadQueries().build();

        messageDao = db.messageDao();

        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);
        sendButton = findViewById(R.id.button_gchat_send);

        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.profile);

            profilePictureView.setImageResource(profilePicture);
            userNameView.setText(userName);
        }


        messages = new ArrayList<Message>();

        listView = findViewById(R.id.recycler_gchat);
        adapter = new MessageListAdapter(getApplicationContext());

        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setMessages(messages);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText mEdit = (EditText)findViewById(R.id.edit_gchat_message);

                String messageContent = mEdit.getText().toString();

                //TODO need to update the data.

                Message message = new Message(1, "TIME-NOW", true, "Sender", messageContent, "Receiver");

                messageDao.insert(message);

                messages.clear();
                messages.addAll(messageDao.index());
                adapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onResume(){
        super.onResume();
        messages.clear();
        messages.addAll(messageDao.index());
        adapter.notifyDataSetChanged();
    }
}