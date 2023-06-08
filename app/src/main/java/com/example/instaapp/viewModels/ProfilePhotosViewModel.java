package com.example.instaapp.viewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.instaapp.api.PhotosApi;
import com.example.instaapp.models.Photo;
import com.example.instaapp.statik.IpConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilePhotosViewModel extends ViewModel {
    private MutableLiveData<List<Photo>> photosList;
    private String username = "";

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUp(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhotosApi getPhotosAPI = retrofit.create(PhotosApi.class);

        Call<List<Photo>> call = getPhotosAPI.getAlbumPhotos(username);

        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                photosList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {
                Log.d("logdev", t.toString());
                photosList.setValue(new ArrayList<>());
            }
        });
    }
    public ProfilePhotosViewModel() {
        this.photosList = new MutableLiveData<>();

    }

    public MutableLiveData<List<Photo>> getObservedPhotos() {
        return photosList;
    }



}
