package com.example.instaapp.statik;

import android.app.Application;

public class LocalUser extends Application {
    private static String name = "";
    private static String showingName = "";
    private static String token = "";

    public static void setName(String newName) {
        name = newName;
    }

    public static void setToken(String newToken) {
        token = newToken;
    }

    public static String getName() {
        return name;
    }

    public static String getToken() {
        return token;
    }

    public static String getShowingName() {
        return showingName;
    }

    public static void setShowingName(String showingName) {
        LocalUser.showingName = showingName;
    }

    public static void logout() {
         token = "";
         showingName = "";
         name = "";
    }
}
