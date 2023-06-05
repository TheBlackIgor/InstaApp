package com.example.instaapp.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaapp.statik.NewPostFile;
import com.example.instaapp.databinding.FragmentAddPostBinding;
import com.example.instaapp.views.CameraActivity;


public class AddPost extends Fragment {

    FragmentAddPostBinding addPostBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addPostBinding = FragmentAddPostBinding.inflate(getLayoutInflater());

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            try {
//                                Imager.bitmap = + MediaStore.Images.Media.getBitmap(AddPhoto.this.getActivity().getContentResolver(), Uri.parse(data.getDataString()));
                                NewPostFile.uri = Uri.parse(data.getDataString());
                                Intent intent = new Intent(getContext(), CameraActivity.class);
                                startActivity(intent);
//                                ((MainActivity)getActivity()).replaceFragment();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            //TODO TUTAJ RETROFIT UPLOAD ZDJECIA ITP
                        }
                    }
                });

        addPostBinding.selectPicture.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");

            someActivityResultLauncher.launch(intent);
        });
        addPostBinding.takePicture.setOnClickListener(v->{
            Intent intent = new Intent(getContext(), CameraActivity.class);
            startActivity(intent);
        });



        return addPostBinding.getRoot();
    }
}