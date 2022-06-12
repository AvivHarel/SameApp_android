package com.example.sameapp.api;

import com.example.sameapp.Contact;
import com.example.sameapp.MyApplication;
import com.example.sameapp.R;
import com.google.gson.GsonBuilder;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ContactsApi {
    //private MutableLiveData<List<Contact>> contactsListData;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public ContactsApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
    // get all contacts from api.
    public void get() {
        Call<List<apiContact>> call = webServiceAPI.getContacts();
        call.enqueue(new Callback<List<apiContact>> (){

            @Override
            public void onResponse(Call<List<apiContact>> call, Response<List<apiContact>> response) {
                List<apiContact> contacts = (List<apiContact>) response.body();
                for (apiContact c : contacts) {
                    String id = c.getId();
                    String last = c.getLast();
                }
                //response.body();
            }

            @Override
            public void onFailure(Call<List<apiContact>> call, Throwable t) {

            }
        });
    }
}