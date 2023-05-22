package com.example.instaapp.models;

import java.util.ArrayList;

public class ClickedPost {
    private static String username;
    private static String postURL;
    private static String description;
    private static ArrayList<Tag> tags;

    public static ArrayList<Tag> getTags() {
        return tags;
    }

    public static void setTags(ArrayList<Tag> tags) {
        ClickedPost.tags = tags;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ClickedPost.username = username;
    }

    public static String getPostURL() {
        return postURL;
    }

    public static void setPostURL(String postURL) {
        ClickedPost.postURL = postURL;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        ClickedPost.description = description;
    }
}
