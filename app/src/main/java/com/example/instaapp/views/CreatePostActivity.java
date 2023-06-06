package com.example.instaapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.instaapp.api.SendImageApi;
import com.example.instaapp.fragments.HomePage;
import com.example.instaapp.statik.FileManager;
import com.example.instaapp.statik.Image;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.LocalUser;
import com.example.instaapp.statik.NewPostFile;
import com.example.instaapp.databinding.ActivityCreatePostBinding;
import com.example.instaapp.models.Photo;
import com.example.instaapp.utils.Dialogs;
import com.example.instaapp.viewModels.HomePageViewModel;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePostActivity extends AppCompatActivity {
    ActivityCreatePostBinding createPostBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createPostBinding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(createPostBinding.getRoot());


        createPostBinding.image.setImageURI(NewPostFile.uri);
        createPostBinding.cancel.setOnClickListener(v->{
            finish();
        });

        createPostBinding.description.setOnClickListener(v->{
            Dialogs.changeNewPhotoDescription(this);
        });

        createPostBinding.tags.setOnClickListener(v->{
            Intent intent = new Intent(this, AddTagsActivity.class);
            startActivity(intent);
        });

        createPostBinding.createPost.setOnClickListener(v->{
            sendImage();
        });

    }
    void sendImage() {
        File file = new File(FileManager.getPathFromUri(this, NewPostFile.uri));
        RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
        RequestBody album = RequestBody.create(MultipartBody.FORM, LocalUser.getName());
        RequestBody description = RequestBody.create(MultipartBody.FORM, createPostBinding.description.getText().toString());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SendImageApi uploadPhotoAPI = retrofit.create(SendImageApi.class);

        Call<Photo> call = uploadPhotoAPI.sendImage(body, album, description);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                Log.d("response", response.body().toString());
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.d("failure", t.toString());

            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}