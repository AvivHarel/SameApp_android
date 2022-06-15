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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.sameapp.api.MessagesApi;
import com.example.sameapp.api.UsersApi;
import com.example.sameapp.api.apiMessage;
import com.example.sameapp.api.apiUser;
import com.example.sameapp.dao.ContactDao;
import com.example.sameapp.dao.MessageDao;
import com.example.sameapp.dao.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserActivity extends AppCompatActivity {

    ImageView profilePictureView;
    TextView userNameView;
    Button sendButton;

    private ContactAppDB db;
    private MessageDao messageDao;
    private ContactDao contactDao;
    private UserDao userDao;
    private MessagesApi messageApi;

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
        messageApi = new MessagesApi(getApplicationContext());


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

        // TODO get list of messages from server

        //messageApi.get(receiver, sender);
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String sender = (sharedpreferences.getString("USERNAME", ""));
        String receiver = activityIntent.getStringExtra("userName");

        Call<List<apiMessage>> call = messageApi.getWebServiceAPI().getMessages(receiver, sender);
        call.enqueue(new Callback<List<apiMessage>> (){

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<List<apiMessage>> call, Response<List<apiMessage>> response) {
                List<apiMessage> messagesList = (List<apiMessage>) response.body();

                if (messagesList != null){
                    for (apiMessage m : messagesList) {

                        Message temp = messageDao.isDataExist(m.getId());
                        if (temp == null){
                            Message message = new Message(m.getCreated(), m.getSent(), m.getUserId(), m.getContent(), m.getContactId());

                            //contacts.add(contact);
                            messageDao.insert(message);
                            contactDao.update(m.getContactId(), m.getContent(), m.getCreated());
                        }
                    }
                }

                messages.clear();
                TextView textViewReceiver = findViewById(R.id.user_text_user_name);
                String receiver = textViewReceiver.getText().toString();
                messages.addAll(messageDao.get(receiver));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<apiMessage>> call, Throwable t) {

            }
        });





        sendButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {

                EditText mEdit = (EditText)findViewById(R.id.edit_gchat_message);

                String messageContent = mEdit.getText().toString();

                if (messageContent.length() > 0) {
                    Time today = new Time(Time.getCurrentTimezone());
                    today.setToNow();

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    String strDate = sdf.format(c.getTime());

                    String receiver = activityIntent.getStringExtra("userName");

                    SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String sender = (sharedpreferences.getString("USERNAME", ""));

                    Message message = new Message(strDate, true, sender, messageContent, receiver);

                    apiMessage apiMessage = new apiMessage(message.getMessageId(),messageContent,strDate,true,sender,receiver);

                    //messageApi.get(receiver, sender);

                    messageApi.getWebServiceAPI().createMessage(apiMessage, receiver).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200){
                                contactDao.update(receiver,messageContent,strDate);
                                messageDao.insert(message);
                                messages.clear();
                                messages.addAll(messageDao.get(receiver));
                                adapter.notifyDataSetChanged();
                                mEdit.setText("");

                            }
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });
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