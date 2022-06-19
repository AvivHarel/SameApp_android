package com.example.sameapp.api;

import android.content.Context;

import com.example.sameapp.MyApplication;
import com.example.sameapp.R;

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
