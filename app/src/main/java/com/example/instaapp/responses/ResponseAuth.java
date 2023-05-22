package com.example.instaapp.responses;

public class ResponseAuth {
    private boolean success;
    public ResponseAuth(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
