package com.example.instaapp.api;

import com.example.instaapp.models.Photo;
import com.example.instaapp.models.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TagsApi {

    @GET("/api/tags")
    Call<List<Tag>> getAllTags();

}
