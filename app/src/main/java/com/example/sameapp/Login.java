package com.example.sameapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sameapp.api.ContactsApi;
import com.example.sameapp.api.UsersApi;

public class Login extends AppCompatActivity {


    Button loginButton;
    Button moveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_loginButton);
        moveButton = findViewById(R.id.move_to_register);

        // when press to login move to list of contact list.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_list.class);

                // TODO check if exist in db.
                //UsersApi usersApi = new UsersApi();
                //usersApi.get();
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