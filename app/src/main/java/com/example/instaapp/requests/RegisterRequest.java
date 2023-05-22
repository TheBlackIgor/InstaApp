package com.example.instaapp.requests;

public class RegisterRequest {
    String name;
    String email;
    String password;

    public RegisterRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
