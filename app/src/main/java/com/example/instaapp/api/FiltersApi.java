package com.example.instaapp.api;

import com.example.instaapp.models.Photo;
import com.example.instaapp.requests.FilterRequest;
import com.example.instaapp.responses.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface FiltersApi {
    @PATCH("/api/filters")
    Call<Photo> addFilter(@Body FilterRequest filterRequest);
}
