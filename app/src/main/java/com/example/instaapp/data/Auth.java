package com.example.instaapp.data;

public class Auth {
    private boolean success;
    public Auth(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
