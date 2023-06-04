package com.example.instaapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.instaapp.api.SendImageApi;
import com.example.instaapp.data.Image;
import com.example.instaapp.data.IpConfig;
import com.example.instaapp.data.LocalUser;
import com.example.instaapp.data.NewPostFile;
import com.example.instaapp.databinding.ActivityCreatePostBinding;
import com.example.instaapp.models.Photo;

import java.io.File;
import java.io.IOException;

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

        createPostBinding.createPost.setOnClickListener(v->{
            sendImage();
        });

    }
    void sendImage() {
        Log.d("path",Image.uri.getPath());
        File file = new File(Image.uri.getPath());
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
    }

}