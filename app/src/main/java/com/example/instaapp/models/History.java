package com.example.instaapp.models;

import java.util.Date;

public class History {
    public String status;
    public Date timestamp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public History(String status, Date timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }
}
