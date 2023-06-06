package com.example.instaapp.models;

public class Tag {
    public int id;
    public String name;
    public int popularity;

    public Tag(int id, String name, int popularity) {
        this.id = id;
        this.name = name;
        this.popularity = popularity;
    }
}
