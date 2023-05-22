package com.example.instaapp.api;

import com.example.instaapp.data.Auth;
import com.example.instaapp.models.Token;
import com.example.instaapp.requests.LoginRequest;
import com.example.instaapp.requests.RegisterRequest;
import com.example.instaapp.responses.LoginResponse;
import com.example.instaapp.responses.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UsersApi {

    @Headers("Content-Type: application/json")
    @POST("/api/user/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @Headers("Content-Type: application/json")
    @POST("/api/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/user/auth")
    Call<Auth> postAuthData(@Header("Authorization") String token);

//    @FormUrlEncoded
//    @POST("/api/user/getInfo")
//    Call<UserInfoResponse> postUserInfo(@Field("username") String username, @Header("Authorization") String token);
}