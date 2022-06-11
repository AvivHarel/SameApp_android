package com.example.sameapp;

import static com.example.sameapp.Register.MyPREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sameapp.api.ContactsApi;
import com.example.sameapp.api.UsersApi;
import com.example.sameapp.dao.UserDao;

public class Login extends AppCompatActivity {


    Button loginButton;
    TextView moveButton;

    private ContactAppDB db;
    private UserDao userDao;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = Room.databaseBuilder(getApplicationContext(), ContactAppDB.class, "ContactsDB")
                .allowMainThreadQueries().build();

        userDao = db.userDao();

        loginButton = findViewById(R.id.login_loginButton);
        moveButton = findViewById(R.id.move_to_register);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        // when press to login move to list of contact list.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide the keyboard after press.
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                EditText ItemUserName = findViewById(R.id.login_userName);
                EditText ItemPassword = findViewById(R.id.login_password);

                String userName = ItemUserName.getText().toString();
                String password = ItemPassword.getText().toString();

                User user = userDao.get(userName);
                if (userName.length() == 0 || password.length() == 0) {
                    Toast t = Toast.makeText(getApplicationContext(), "UserName or Password are not valid.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else if (user == null || !user.getPassword().equals(password)) {
                    Toast t = Toast.makeText(getApplicationContext(), "UserName or Password Wrong.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("USERNAME", userName);
                    editor.commit();



                    Intent intent = new Intent(getApplicationContext(), activity_list.class);
                    startActivity(intent);
                }
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