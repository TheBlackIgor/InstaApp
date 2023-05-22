package com.example.instaapp.api;

import com.example.instaapp.models.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PhotosApi {
    @GET("/api/photos/{album}")
    Call<List<Photo>> getAllPhotos(@Path("album") String album);

    @GET("/api/photos")
    Call<List<Photo>> getHomePhotos();
}
