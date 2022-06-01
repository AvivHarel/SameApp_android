package com.example.sameapp;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class ContactFormActivity extends AppCompatActivity {

    private ContactAppDB db;
    private  ContactDao contactDao;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);


        db= Room.databaseBuilder(getApplicationContext(),ContactAppDB.class,"ContactsDB")
                .allowMainThreadQueries().build();

        contactDao = db.contactDao();

        Button btnAdd = findViewById(R.id.contact_btnAdd);

        btnAdd.setOnClickListener(view->{

            EditText userName = findViewById(R.id.contact_userName);
            EditText nickname = findViewById(R.id.contact_nickname);
            EditText server = findViewById(R.id.contact_server);

            //need to edit what we create, just for now.

            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String strDate = sdf.format(c.getTime());

            Contact contact = new Contact(userName.getText().toString(),R.drawable.profile,"",strDate);

            contactDao.insert(contact);

            finish();
        });
    }
}