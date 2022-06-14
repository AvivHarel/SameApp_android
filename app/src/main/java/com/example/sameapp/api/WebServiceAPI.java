package com.example.sameapp.api;

import androidx.annotation.AnyRes;

import com.example.sameapp.Contact;
import com.example.sameapp.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("api/contacts")
    Call<List<apiContact>> getContacts();

    @GET("api/users")
    Call<List<apiUser>> getUsers();

    @GET("api/contacts/{id}/messages")
    Call<List<apiMessage>> getMessges(@Path("id") String id);

    @Headers("Content-Type: application/json")
    @POST("api/contacts")
    Call<Void> createContact(@Body apiContact contact);

    @Headers("Content-Type: application/json")
    @POST("api/users")
    Call<Void> createUser(@Body apiUser user);

    @Headers("Content-Type: application/json")
    @POST("api/users/login")
    Call<Void> login(@Body apiUser apiUser);

    @Headers("Content-Type: application/json")
    @POST("api/contacts/{id}/messages")
    Call<Void> createMessage(@Body apiMessage message, @Path("id") String id);



    @DELETE("contacts/{id}")
    Call<Void> deleteContact(@Path("id") int id);
}