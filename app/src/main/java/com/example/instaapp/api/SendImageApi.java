package com.example.instaapp.api;

import com.example.instaapp.models.Photo;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SendImageApi {
    @Multipart
    @POST("api/photos")
    Call<Photo> sendImage(
            @Part MultipartBody.Part file,
            @Part("album") RequestBody album,
            @Part("description") RequestBody description
    );
}
