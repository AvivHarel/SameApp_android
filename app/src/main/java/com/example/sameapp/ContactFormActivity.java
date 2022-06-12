package com.example.sameapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.sameapp.Register.MyPREFERENCES;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sameapp.dao.ContactDao;
import com.example.sameapp.dao.UserDao;

public class ContactFormActivity extends AppCompatActivity {

    private ContactAppDB db;
    private ContactDao contactDao;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);


        db = Room.databaseBuilder(getApplicationContext(),ContactAppDB.class,"ContactsDB")
                .allowMainThreadQueries().build();

        contactDao = db.contactDao();
        userDao = db.userDao();

        Button btnAdd = findViewById(R.id.contact_btnAdd);

        btnAdd.setOnClickListener(view->{

            EditText userName = findViewById(R.id.contact_userName);
            EditText nickname = findViewById(R.id.contact_nickname);
            EditText server = findViewById(R.id.contact_server);

            //check if the contact exist in the useres db.
            User userExist = userDao.get(userName.getText().toString());
            if(userExist != null) {


                //TODO need to edit what we create, just for now.

                Time today = new Time(Time.getCurrentTimezone());
                today.setToNow();

                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
                String strDate = sdf.format(c.getTime());

                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                String owner = (sharedpreferences.getString("USERNAME", ""));

                Contact contact = new Contact(userName.getText().toString(), R.drawable.profile, "", strDate, owner);

                contactDao.insert(contact);

                finish();
            }
            else{
                // hide the keyboard after press.
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                Toast t = Toast.makeText(getApplicationContext(), "Contact is not exist in the app db.", Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }
}