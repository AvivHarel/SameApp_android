package com.example.sameapp.api;

import com.example.sameapp.Contact;
import com.example.sameapp.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("contacts")
    Call<List<apiContact>> getContacts();

    @GET("contacts/{id}/messages")
    Call<List<apiMessage>> getMessges(@Path("id") String id);

    @POST("contacts")
    Call<Void> createContact(@Body Contact contact);

    @DELETE("contacts/{id}")
    Call<Void> deleteContact(@Path("id") int id);
}