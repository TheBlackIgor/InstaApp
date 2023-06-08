package com.example.instaapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp.api.FiltersApi;
import com.example.instaapp.api.SendImageApi;
import com.example.instaapp.models.Photo;
import com.example.instaapp.models.PostType;
import com.example.instaapp.requests.FilterRequest;
import com.example.instaapp.statik.FileManager;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.LocalUser;
import com.example.instaapp.statik.NewPostFile;
import com.example.instaapp.statik.PickedPhoto;
import com.example.instaapp.databinding.FragmentPostBinding;
import com.example.instaapp.views.ShowLocalizationActivity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Post extends Fragment {

    FragmentPostBinding postBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        postBinding = FragmentPostBinding.inflate(getLayoutInflater());

        postBinding.username.setText(PickedPhoto.getUsername());
        postBinding.description.setText(PickedPhoto.getDescription());
        postBinding.localization.setOnClickListener(v->{
            if(PickedPhoto.getLocalization() != null && PickedPhoto.getLocalization() != "null"){
                Intent intent = new Intent(getContext(), ShowLocalizationActivity.class);
                startActivity(intent);
            }
        });

        if(!PickedPhoto.getUsername().equals(LocalUser.getName()) || PickedPhoto.filetype != PostType.PHOTO){
            postBinding.filters.getLayoutParams().height = 0;
        }

        Log.d("ImageUrl",PickedPhoto.getPostURL());

//        if (PickedPhoto.filetype == PostType.PHOTO) {
//            ImageView imageView;
//            imageView = new ImageView(getContext());
//            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            postBinding.postContent.addView(imageView);
////            imageView.setImageURI(NewPostFile.uri);
////            Glide.with(imageView.getContext()).load(uri).into(imageView);
//            Glide.with(imageView.getContext())
//                    .load(PickedPhoto.getPostURL())
//                    .into(imageView);
//        } else {
//            VideoView videoView = new VideoView(getContext());
//            videoView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            postBinding.postContent.addView(videoView);
//
//            videoView.setVideoURI(NewPostFile.uri);
//            MediaController mediaController = new MediaController(getContext());
//
//            mediaController.setAnchorView(videoView);
//            mediaController.setMediaPlayer(videoView);
//            videoView.setMediaController(mediaController);
//            videoView.start();
//        }
        if (PickedPhoto.filetype == PostType.PHOTO) {
            postBinding.video.getLayoutParams().height = 0;
            Glide.with(this)
                    .load(PickedPhoto.getPostURL())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(postBinding.image);
        }
        else{
            postBinding.image.getLayoutParams().height = 0;
            ExoPlayer player = new ExoPlayer.Builder(this.getContext()).build();
            postBinding.video.setPlayer(player);
            MediaItem mediaItem = MediaItem.fromUri(PickedPhoto.getPostURL());
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();
        }

        String tags = "";

        for (int i = 0; i < PickedPhoto.getTags().size(); i++) {
            tags += PickedPhoto.getTags().get(i) + " ";
        }
        postBinding.tags.setText(tags);

        postBinding.flip.setOnClickListener(v->{
            filter("flip");
        });

        postBinding.BNW.setOnClickListener(v->{
            filter("grayscale");
        });

        postBinding.negate.setOnClickListener(v->{
            filter("negate");
        });


        return postBinding.getRoot();



    }

    void filter(String type) {
        FilterRequest filterRequest = new FilterRequest(PickedPhoto.photo.getId(), PickedPhoto.photo.getUrl(), type, "");
        Log.d("FilterRequest", filterRequest.toString());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FiltersApi filtersApi = retrofit.create(FiltersApi.class);

        Call<Photo> call = filtersApi.addFilter(filterRequest);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                Log.d("RESPONSE", response.body().toString());
                Glide.with(getContext())
                        .load(IpConfig.getIp()+"/api/photos/"+response.body().getId())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(postBinding.image);
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {
                Log.d("failure", t.toString());
            }
        });
    }

}