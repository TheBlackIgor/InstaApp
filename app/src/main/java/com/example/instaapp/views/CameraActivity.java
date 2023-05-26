package com.example.instaapp.views  ;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.instaapp.data.NewPostFile;
import com.example.instaapp.databinding.ActivityCameraBinding;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

public class CameraActivity extends AppCompatActivity {

    ActivityCameraBinding cameraBinding;

    private String[] REQUIRED_PERMISSIONS = new String[]{
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"};

    private int PERMISSIONS_REQUEST_CODE = 100;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraBinding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(cameraBinding.getRoot());

        if (!checkIfPermissionsGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                if(checkIfPermissionsGranted()) startCamera();
            }
        } else {
            startCamera();
        }

        cameraBinding.takePicture.setOnClickListener(v->{
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "file_name_from_date");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");

            ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(
                            getContentResolver(),
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues)
                            .build();
            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                    new ImageCapture.OnImageSavedCallback() {
                        @Override
                        public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                            Log.d("Image", outputFileResults.toString());
                            NewPostFile.image = outputFileResults;

                            Intent intent = new Intent(CameraActivity.this, CreatePostActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(@NonNull ImageCaptureException exception) {
                            // error
                        }
                    });
        });

    }

    private boolean checkIfPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(CameraActivity.this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (InterruptedException | ExecutionException e) {
                // No errors need to be handled for this Future. This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));
    }
    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();

        imageCapture =
                new ImageCapture.Builder()
                        .setTargetRotation(cameraBinding.camera.getDisplay().getRotation())
                        .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(cameraBinding.camera.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, imageCapture, preview);
    }

}
