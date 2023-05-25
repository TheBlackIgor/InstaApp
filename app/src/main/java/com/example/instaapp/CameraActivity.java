package com.example.instaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.instaapp.databinding.ActivityCameraBinding;

public class CameraActivity extends AppCompatActivity {

    ActivityCameraBinding cameraBinding;

    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    private int PERMISSIONS_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraBinding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(cameraBinding.getRoot());

        if (!checkIfPermissionsGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        } else {
            // start kamery
        }
    }

    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}