package com.example.sameapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private static final int Img_id = 1;
    Button registerButton;
    Button moveButton;
    Button uploadButton;
    ImageView imageToUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                Intent intent = new Intent(getApplicationContext(), activity_list.class);
                startActivity(intent);
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