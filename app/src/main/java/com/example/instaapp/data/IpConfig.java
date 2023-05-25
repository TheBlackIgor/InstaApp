package com.example.instaapp.data;

public class IpConfig {
    private static String ip = "192.168.0.154";

    public static void setIp(String newIp) {
        ip = newIp;
    }

    public static String getRawIp() {
        return ip;
    }

    public static String getIp() {
        return "http://"+ip+":5000";
    }
}