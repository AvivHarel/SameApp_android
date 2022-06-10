package com.example.sameapp.api;
import com.example.sameapp.Contact;
import com.example.sameapp.MyApplication;
import com.example.sameapp.R;
import com.example.sameapp.User;
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

    public UsersApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
    // get all users from api.
    public void get() {
        Call<List<User>> call = webServiceAPI.getUsers();
        call.enqueue(new Callback<List<User>> (){

            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                //response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
