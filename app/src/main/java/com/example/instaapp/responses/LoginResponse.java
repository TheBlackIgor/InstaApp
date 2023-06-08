package com.example.instaapp.responses;

public class LoginResponse {
    private boolean success;
    private String token;
    private String name;
    public String showingName;

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public LoginResponse(String token){
        this.token = token;
    }

    public LoginResponse(boolean success, String token, String username) {
        this.success = success;
        this.token = token;
        this.name = username;
    }

    public LoginResponse(boolean success, String token) {
        this.success = success;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getShowingName() {
        return showingName;
    }

    public void setShowingName(String showingName) {
        this.showingName = showingName;
    }
}
