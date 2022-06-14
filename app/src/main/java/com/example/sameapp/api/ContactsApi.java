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

    public void create(String Id, String UserNameOwner,
                       String Name, String Server, String Last, String LastDate) {

        apiContact apiContact = new apiContact(Id,UserNameOwner, Name, Server, Last, LastDate);
        webServiceAPI.createContact(apiContact).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200){
                    Contact contact = new Contact(Id, Last, LastDate, UserNameOwner);
                    contactDao.insert(contact);
                    //Intent intent = new Intent(context, activity_list.class);
                    //context.startActivity(intent);
                }
                else{
                        Toast t = Toast.makeText(context, "Contact is not exist in the app db.", Toast.LENGTH_SHORT);
                        t.show();
                    }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
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