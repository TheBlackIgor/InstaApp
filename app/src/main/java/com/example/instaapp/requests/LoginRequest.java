package com.example.instaapp.requests;

public class LoginRequest {
    private String name;
    private String password;

    public LoginRequest(String name, String password){
        this.name = name;
        this.password = password;
    }
}
