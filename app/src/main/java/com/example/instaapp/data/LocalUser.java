package com.example.instaapp.data;

import android.app.Application;

public class LocalUser extends Application {
    private static String username = "";
    private static String token = "";

    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static String getUsername() {
        return username;
    }

    public static String getToken() {
        return token;
    }

    public static void logout() {
         token = "";
         username = "";
    }
}
