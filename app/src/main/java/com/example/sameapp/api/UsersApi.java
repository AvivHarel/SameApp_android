package com.example.sameapp.api;
import static com.example.sameapp.Register.MyPREFERENCES;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.AnyRes;

import com.example.sameapp.Contact;
import com.example.sameapp.MyApplication;
import com.example.sameapp.R;
import com.example.sameapp.User;
import com.example.sameapp.activity_list;
import com.google.gson.GsonBuilder;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersApi {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    public Context context;

    public UsersApi(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.context = context;
    }

    public WebServiceAPI getWebServiceAPI() {
        return webServiceAPI;
    }

}
