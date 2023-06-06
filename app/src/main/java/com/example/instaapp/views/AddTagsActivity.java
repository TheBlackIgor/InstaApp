package com.example.instaapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.instaapp.api.SendImageApi;
import com.example.instaapp.api.TagsApi;
import com.example.instaapp.databinding.ActivityAddTagsBinding;
import com.example.instaapp.models.Photo;
import com.example.instaapp.models.Tag;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.NewPostFile;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTagsActivity extends AppCompatActivity {
    ActivityAddTagsBinding addTagsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addTagsBinding = ActivityAddTagsBinding.inflate(getLayoutInflater());
        setContentView(addTagsBinding.getRoot());

        ArrayList<String> tagsList = NewPostFile.tags;

        addTagsBinding.choiceGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {


            for (int i = 0; i < group.getChildCount(); i++) {
                Chip chip = (Chip) group.getChildAt(i);
                chip.setOnCheckedChangeListener((compoundButton, b) -> {
                });
                if (chip.isChecked()) {
                    chip.setBackgroundColor(0xFF22FF);
                }
            }

        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TagsApi getTags = retrofit.create(TagsApi.class);

        Call<List<Tag>> call = getTags.getAllTags();

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    Chip chip = new Chip(AddTagsActivity.this);
                    chip.setCheckable(true);

                    chip.setText(response.body().get(i).name);

                    if (tagsList.contains(response.body().get(i).name)) {
                        chip.setChecked(true);
                    }

                    addTagsBinding.choiceGroup.addView(chip);
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {

            }
        });

        addTagsBinding.addTags.setOnClickListener(v -> {
            NewPostFile.tags = new ArrayList<>();
            for (int i = 0; i < addTagsBinding.choiceGroup.getChildCount(); i++) {

                Chip chip = (Chip) addTagsBinding.choiceGroup.getChildAt(i);
                if (chip.isChecked()) {
                    NewPostFile.tags.add(chip.getText().toString());
                }
            }
            finish();
        });
    }
}