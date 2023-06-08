package com.example.instaapp.requests;

public class FilterRequest {
    long id;
    String url;
    String type;
    String params = null;

    public FilterRequest(long id, String url, String type, String params) {
        this.id = id;
        this.url = url;
        this.type = type;
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "FilterRequest{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", params='" + params + '\'' +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
