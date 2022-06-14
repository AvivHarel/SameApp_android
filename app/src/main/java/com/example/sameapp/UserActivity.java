package com.example.sameapp;


import static com.example.sameapp.Register.MyPREFERENCES;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.sameapp.dao.ContactDao;
import com.example.sameapp.dao.MessageDao;
import com.example.sameapp.dao.UserDao;

import java.util.ArrayList;
import java.util.Random;


public class UserActivity extends AppCompatActivity {

    ImageView profilePictureView;
    TextView userNameView;
    Button sendButton;

    private ContactAppDB db;
    private MessageDao messageDao;
    private ContactDao contactDao;
    private UserDao userDao;

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
        contactDao = db.contactDao();
        userDao = db.userDao();

        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);
        sendButton = findViewById(R.id.button_gchat_send);

        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            String receiver = activityIntent.getStringExtra("userName");
            int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.profile);

            // get the user to restore his profile picture:
            User user = userDao.get(receiver);
            String profileImage = user.getPictureId();
            Uri profileUri = Uri.parse(profileImage);
            profilePictureView.setImageURI(profileUri);

            userNameView.setText(receiver);
        }


        messages = new ArrayList<Message>();

        listView = findViewById(R.id.recycler_gchat);
        adapter = new MessageListAdapter(getApplicationContext());

        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setMessages(messages);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {

                EditText mEdit = (EditText)findViewById(R.id.edit_gchat_message);

                String messageContent = mEdit.getText().toString();

                if (messageContent.length() > 0) {
                    Time today = new Time(Time.getCurrentTimezone());
                    today.setToNow();

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                    String strDate = sdf.format(c.getTime());

                    String receiver = activityIntent.getStringExtra("userName");

                    contactDao.update(receiver,messageContent,strDate);

                    SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String sender = (sharedpreferences.getString("USERNAME", ""));

                    Message message = new Message(strDate, true, sender, messageContent, receiver);

                    messageDao.insert(message);

                    messages.clear();
                    messages.addAll(messageDao.get(receiver));
                    adapter.notifyDataSetChanged();

                    mEdit.setText("");
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume(){
        super.onResume();
        messages.clear();
        TextView textViewReceiver = findViewById(R.id.user_text_user_name);
        String receiver = textViewReceiver.getText().toString();
        messages.addAll(messageDao.get(receiver));
        adapter.notifyDataSetChanged();
    }
}