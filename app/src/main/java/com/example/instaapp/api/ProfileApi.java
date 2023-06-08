package com.example.instaapp.api;

import com.example.instaapp.models.User;
import com.example.instaapp.requests.FilterRequest;
import com.example.instaapp.requests.RequestChangeName;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileApi {
    @Multipart
    @POST("/api/profile")
    Call<User> uploadFile(@Part MultipartBody.Part file, @Part("album") RequestBody album);

    @PATCH("/api/profile")
    Call<User> changeName(@Body RequestChangeName changeName);
}
