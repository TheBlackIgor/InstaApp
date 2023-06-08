package com.example.instaapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp.statik.PickedPhoto;
import com.example.instaapp.databinding.FragmentPostBinding;
import com.example.instaapp.views.ShowLocalizationActivity;

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
                Log.d("Loc123", "XDDDDDD");
                startActivity(intent);
            }
        });

        Log.d("desc", PickedPhoto.getDescription());
        Log.d("ImageUrl",PickedPhoto.getPostURL());
        Glide.with(this)
                .load(PickedPhoto.getPostURL())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(postBinding.image);
        String tags = "";

        for (int i = 0; i < PickedPhoto.getTags().size(); i++) {
            tags += PickedPhoto.getTags().get(i) + " ";
        }
        postBinding.tags.setText(tags);


        return postBinding.getRoot();
    }
}