package com.example.instaapp.requests;

public class RequestChangeName {
    private String name;
    private String newName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public RequestChangeName(String name, String newName) {
        this.name = name;
        this.newName = newName;
    }
}
