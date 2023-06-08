package com.example.instaapp.models;

import java.util.ArrayList;

public class Photo {
    private long id;
    private String album;
    private String originalName;
    private String url;
    private String lastChange;
    private ArrayList<History> history;
    private ArrayList<String> tags;
    private String localization;
    private String description;


    public Photo(long id, String album, String originalName, String url, String lastChange, ArrayList<History> history,String description, ArrayList<String> tags,String localization) {
        this.id = id;
        this.album = album;
        this.originalName = originalName;
        this.url = url;
        this.lastChange = lastChange;
        this.description = description;
        this.history = history;
        this.tags = tags;
        this.localization = localization;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    public ArrayList<History> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<History> history) {
        this.history = history;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", album='" + album + '\'' +
                ", originalName='" + originalName + '\'' +
                ", url='" + url + '\'' +
                ", lastChange='" + lastChange + '\'' +
                ", history=" + history +
                ", tags=" + tags +
                '}';
    }
}
