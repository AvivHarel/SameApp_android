package com.example.sameapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.Button;
import android.widget.EditText;
import static com.example.sameapp.Register.MyPREFERENCES;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sameapp.dao.ContactDao;

public class ContactFormActivity extends AppCompatActivity {

    private ContactAppDB db;
    private ContactDao contactDao;

//    public ContactFormActivity() {
//        db = Room.databaseBuilder(getApplicationContext(),ContactAppDB.class,"ContactsDB")
//                .allowMainThreadQueries().build();
//
//        contactDao = db.contactDao();
//    }

//    public void insert(ContactWithMessages contactWithMessages) {
//        new insertAsync(contactDao).execute(contactWithMessages);
//    }
//
//    private static class insertAsync extends AsyncTask<ContactWithMessages, Void, Void> {
//        private ContactDao contactDaoAsync;
//
//        insertAsync(ContactDao courseDao) {
//            contactDaoAsync = courseDao;
//        }
//
//        @Override
//        protected Void doInBackground(ContactWithMessages... contactWithMessages) {
//
//            String identifier = contactDaoAsync.insertContact(contactWithMessages[0].contact);
//
//            for (Message message : contactWithMessages[0].messages) {
//                message.setContactId(identifier);
//            }
//            contactDaoAsync.insertMessages(contactWithMessages[0].messages);
//            return null;
//        }
//    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);


        db = Room.databaseBuilder(getApplicationContext(),ContactAppDB.class,"ContactsDB")
                .allowMainThreadQueries().build();

        contactDao = db.contactDao();

        Button btnAdd = findViewById(R.id.contact_btnAdd);

        btnAdd.setOnClickListener(view->{

            EditText userName = findViewById(R.id.contact_userName);
            EditText nickname = findViewById(R.id.contact_nickname);
            EditText server = findViewById(R.id.contact_server);

            //TODO need to edit what we create, just for now.

            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
            String strDate = sdf.format(c.getTime());

            SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

            String owner = (sharedpreferences.getString("USERNAME", ""));

            Contact contact = new Contact(userName.getText().toString(),R.drawable.profile,"",strDate, owner);

            contactDao.insert(contact);

            finish();
        });
    }
}