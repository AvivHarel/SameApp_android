package com.example.sameapp;

import android.content.Context;
import android.content.Intent;
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


import androidx.annotation.AnyRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sameapp.api.ContactsApi;
import com.example.sameapp.api.UsersApi;
import com.example.sameapp.api.apiContact;
import com.example.sameapp.dao.ContactDao;
import com.example.sameapp.dao.UserDao;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactFormActivity extends AppCompatActivity {

    private ContactAppDB db;
    private ContactDao contactDao;
    private UserDao userDao;
    private ContactsApi contactsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);


        db = Room.databaseBuilder(getApplicationContext(),ContactAppDB.class,"ContactsDB")
                .allowMainThreadQueries().build();

        contactDao = db.contactDao();
        userDao = db.userDao();
        contactsApi = new ContactsApi(getApplicationContext(), contactDao);


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

            Contact contact = new Contact(userName.getText().toString(), "", strDate, owner);

            apiContact apiContact = new apiContact(userName.getText().toString(), owner,
                    nickname.getText().toString(), server.getText().toString(), "" ,strDate);


            contactsApi.getWebServiceAPI().createContact(apiContact).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 200){
                        contactDao.insert(contact);
                        finish();
                    }
                    else{
                        Toast t = Toast.makeText(getApplicationContext(), "Contact is not exist in the app db.", Toast.LENGTH_SHORT);
                        t.show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });

        });
    }
}