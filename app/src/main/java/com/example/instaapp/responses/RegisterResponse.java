package com.example.instaapp.responses;

public class RegisterResponse {
    String token;

    public RegisterResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
