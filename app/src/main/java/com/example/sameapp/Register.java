package com.example.sameapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sameapp.dao.MessageDao;
import com.example.sameapp.dao.UserDao;

import java.util.List;

public class Register extends AppCompatActivity {

    private static final int Img_id = 1;
    Button registerButton;
    TextView moveButton;
    Button uploadButton;
    ImageView imageToUpload;

    private ContactAppDB db;
    private UserDao userDao;

    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = Room.databaseBuilder(getApplicationContext(), ContactAppDB.class, "ContactsDB")
                .allowMainThreadQueries().build();

        userDao = db.userDao();

        registerButton = findViewById(R.id.register_registerButton);
        moveButton = findViewById(R.id.move_to_register);
        uploadButton = findViewById(R.id.uploadPicBtn);
        imageToUpload = findViewById(R.id.imageToUpload);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,Img_id);

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hide the keyboard after press.
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                EditText ItemUserName = findViewById(R.id.register_userName);
                EditText ItemPassword = findViewById(R.id.register_password);
                EditText ItemConfirmPassword = findViewById(R.id.confirm_password);
                ImageView ItemPicture = findViewById(R.id.imageToUpload);

                String userName = ItemUserName.getText().toString();
                String password = ItemPassword.getText().toString();
                String confirmPassword = ItemConfirmPassword.getText().toString();

                // TODO maybe change the first param.
                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                //TODO need to change.
                String myImg = "TEMP";
                User user = userDao.get(userName);

                if (userName.length() == 0 || password.length() == 0) {
                    Toast t = Toast.makeText(getApplicationContext(), "UserName or Password are not valid.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else if (!password.equals(confirmPassword)) {
                    Toast t = Toast.makeText(getApplicationContext(), "Password not equal.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else if (user != null){
                    Toast t = Toast.makeText(getApplicationContext(), "UserName already exist.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else if (password.length() < 8) {
                    Toast t = Toast.makeText(getApplicationContext(), "Password must contains minimum 8 chars.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else{
                    User newUser = new User(userName, password, myImg);
                    userDao.insert(newUser);

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
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requsetCode, int resultCode,Intent data){
        super.onActivityResult(requsetCode,resultCode,data);
        if(requsetCode == Img_id && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageToUpload.setImageURI(selectedImage);
        }
    }
}