package com.example.sameapp.api;

import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.AnyRes;

import com.example.sameapp.Contact;
import com.example.sameapp.ContactAppDB;
import com.example.sameapp.MyApplication;
import com.example.sameapp.R;
import com.example.sameapp.activity_list;
import com.example.sameapp.dao.ContactDao;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ContactsApi {
    //private MutableLiveData<List<Contact>> contactsListData;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;
    Context context;
    ContactDao contactDao;


    public ContactsApi(Context context, ContactDao contactDao) {
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.context = context;
        this.contactDao = contactDao;

    }

    public WebServiceAPI getWebServiceAPI() {
        return webServiceAPI;
    }

    // get all contacts from api.
    public void get(String userName) {
        Call<List<apiContact>> call = webServiceAPI.getContacts(userName);
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