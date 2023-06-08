package com.example.instaapp.models;

import java.util.Date;

public class History {
    public String status;
    public long timestamp;
    public String url;

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

    public History(String status, long timestamp, String url) {
        this.status = status;
        this.timestamp = timestamp;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
