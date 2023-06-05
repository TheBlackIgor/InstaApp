package com.example.instaapp.statik;

import com.example.instaapp.models.Tag;

import java.util.ArrayList;

public class PickedPhoto {
    private static String username;
    private static String postURL;
    private static String description;
    private static ArrayList<Tag> tags;

    public static ArrayList<Tag> getTags() {
        return tags;
    }

    public static void setTags(ArrayList<Tag> tags) {
        PickedPhoto.tags = tags;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        PickedPhoto.username = username;
    }

    public static String getPostURL() {
        return postURL;
    }

    public static void setPostURL(String postURL) {
        PickedPhoto.postURL = postURL;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        PickedPhoto.description = description;
    }
}
