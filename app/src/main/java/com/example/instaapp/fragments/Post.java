package com.example.instaapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instaapp.R;
import com.example.instaapp.data.PickedPhoto;
import com.example.instaapp.data.IpConfig;
import com.example.instaapp.databinding.FragmentPostBinding;

public class Post extends Fragment {

    FragmentPostBinding postBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        postBinding = FragmentPostBinding.inflate(getLayoutInflater());

        postBinding.username.setText(PickedPhoto.getUsername());
        postBinding.description.setText(PickedPhoto.getDescription());

        Glide.with(this)
                .load(PickedPhoto.getPostURL())
                .into(postBinding.image);

        LinearLayout linearLayout = postBinding.tags;

        for (int i = 0; i < PickedPhoto.getTags().size(); i++) {
            // Create a new TextView
            TextView textView = new TextView(getContext());

            // Set text value
            textView.setText(PickedPhoto.getTags().get(i).name);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            layoutParams.setMargins(0, 16, 0, 16);

            // Apply layout parameters to the TextView
            textView.setLayoutParams(layoutParams);

            postBinding.tags.addView(textView);
        }

        return postBinding.getRoot();
    }
}