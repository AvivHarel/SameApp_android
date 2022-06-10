package com.example.sameapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sameapp.api.ContactsApi;
import com.example.sameapp.api.UsersApi;
import com.example.sameapp.dao.UserDao;

public class Login extends AppCompatActivity {


    Button loginButton;
    Button moveButton;

    private ContactAppDB db;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = Room.databaseBuilder(getApplicationContext(), ContactAppDB.class, "ContactsDB")
                .allowMainThreadQueries().build();

        userDao = db.userDao();

        loginButton = findViewById(R.id.login_loginButton);
        moveButton = findViewById(R.id.move_to_register);

        // when press to login move to list of contact list.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText ItemUserName = findViewById(R.id.login_userName);
                EditText ItemPassword = findViewById(R.id.login_password);

                String userName = ItemUserName.getText().toString();
                String password = ItemPassword.getText().toString();

                User user = userDao.get(userName);
                if (userName.length() == 0 || password.length() == 0 || user == null || !user.getPassword().equals(password)){
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), activity_list.class);
                startActivity(intent);
            }
        });

        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });

    }
}