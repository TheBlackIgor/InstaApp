package com.example.instaapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.instaapp.data.NewPostFile;
import com.example.instaapp.databinding.ActivityCreatePostBinding;

public class CreatePostActivity extends AppCompatActivity {
    ActivityCreatePostBinding createPostBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPostBinding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(createPostBinding.getRoot());


        createPostBinding.image.setImageURI(NewPostFile.uri);

    }
}