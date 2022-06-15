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

import androidx.annotation.AnyRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.sameapp.api.UsersApi;
import com.example.sameapp.api.apiUser;
import com.example.sameapp.dao.MessageDao;
import com.example.sameapp.dao.UserDao;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private static final int Img_id = 1;
    Button registerButton;
    TextView moveButton;
    Button uploadButton;
    ImageView imageToUpload;

    private ContactAppDB db;
    private UserDao userDao;
    private UsersApi usersApi;

    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = Room.databaseBuilder(getApplicationContext(), ContactAppDB.class, "ContactsDB")
                .allowMainThreadQueries().build();

        userDao = db.userDao();
        usersApi = new UsersApi(getApplicationContext());

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
                //String myImg = imageToUpload.getTag().toString();
                String myImg = "";
                User user = userDao.get(userName);

//                String pattern ="([a-zA-Z])";
//                Pattern r = Pattern.compile(pattern);
//
//                Matcher m = r.matcher(password);

                if (password.length() == 0) {
                    Toast t = Toast.makeText(getApplicationContext(), "Password is a Required field.", Toast.LENGTH_SHORT);
                    t.show();
                }
                if (userName.length() == 0) {
                    Toast t = Toast.makeText(getApplicationContext(), "Please choose user name.", Toast.LENGTH_SHORT);
                    t.show();
                }
                else if (!password.equals(confirmPassword)) {
                    Toast t = Toast.makeText(getApplicationContext(), "Passwords are not equal.", Toast.LENGTH_SHORT);
                    t.show();
                }
//                else if (user != null){
//                    Toast t = Toast.makeText(getApplicationContext(), "UserName already exist.", Toast.LENGTH_SHORT);
//                    t.show();
//                }
                else if (password.length() < 8) {
                    Toast t = Toast.makeText(getApplicationContext(), "Password must contain at least 8 letters.", Toast.LENGTH_SHORT);
                    t.show();
                }
//                else if (!m.find()) {
//                    Toast t = Toast.makeText(getApplicationContext(), "Password must contain at least 1 letter.", Toast.LENGTH_SHORT);
//                    t.show();
//                }
                else{

                    User newUser = new User(userName, password, myImg);
                    apiUser apiUser = new apiUser(userName, password);

                    usersApi.getWebServiceAPI().createUser(apiUser).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200){
                                userDao.insert(newUser);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("USERNAME", userName);
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), activity_list.class);
                                startActivity(intent);
                            }else{
                                Toast t = Toast.makeText(getApplicationContext(), "UserName is already exist please choose new name.", Toast.LENGTH_SHORT);
                                t.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
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
            imageToUpload.setTag(selectedImage.toString());
        }
    }
}