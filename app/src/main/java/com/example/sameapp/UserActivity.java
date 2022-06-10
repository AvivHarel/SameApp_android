package com.example.sameapp;


import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
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

import java.util.ArrayList;
import java.util.Random;


public class UserActivity extends AppCompatActivity {

    ImageView profilePictureView;
    TextView userNameView;
    Button sendButton;

    private ContactAppDB db;
    private MessageDao messageDao;
    private ContactDao contactDao;

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

        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);
        sendButton = findViewById(R.id.button_gchat_send);

        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            String receiver = activityIntent.getStringExtra("userName");
            int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.profile);

            profilePictureView.setImageResource(profilePicture);
            userNameView.setText(receiver);
        }


        messages = new ArrayList<Message>();

        listView = findViewById(R.id.recycler_gchat);
        adapter = new MessageListAdapter(getApplicationContext());

        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setMessages(messages);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                EditText mEdit = (EditText)findViewById(R.id.edit_gchat_message);

                String messageContent = mEdit.getText().toString();

                //TODO need to update the data.
                //Random random = new Random();
                //int randomId = random.nextInt(99);

                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                String strDate = sdf.format(c.getTime());

                String receiver = activityIntent.getStringExtra("userName");

                contactDao.update(receiver,messageContent,strDate);

                Message message = new Message(strDate, true, "Sender", messageContent, receiver);

                messageDao.insert(message);

                messages.clear();
                messages.addAll(messageDao.get(receiver));
                adapter.notifyDataSetChanged();

                mEdit.setText("");

            }
        });
    }

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