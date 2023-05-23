package com.example.instaapp.models;

import java.util.Date;

public class History {
    public String status;
    public long timestamp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public History(String status, long timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }
}
