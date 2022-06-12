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


public class MessagesApi {
    //private MutableLiveData<List<Contact>> contactsListData;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    public MessagesApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }
    // get all contacts from api.
    public void get(String contactId) {
        Call<List<apiMessage>> call = webServiceAPI.getMessges(contactId);
        call.enqueue(new Callback<List<apiMessage>> (){

            @Override
            public void onResponse(Call<List<apiMessage>> call, Response<List<apiMessage>> response) {
                List<apiMessage> messages = (List<apiMessage>) response.body();
                for (apiMessage c : messages) {
                    String id = c.getContactId();
                    String last = c.getContent();
                }
                //response.body();
            }

            @Override
            public void onFailure(Call<List<apiMessage>> call, Throwable t) {

            }
        });
    }
}