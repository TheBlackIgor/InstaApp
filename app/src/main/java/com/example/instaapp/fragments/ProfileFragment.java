package com.example.instaapp.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp.R;
import com.example.instaapp.adapters.AdapterProfilePics;
import com.example.instaapp.adapters.HomePageAdapter;
import com.example.instaapp.api.ProfileApi;
import com.example.instaapp.databinding.FragmentHomePageBinding;
import com.example.instaapp.databinding.FragmentProfileBinding;
import com.example.instaapp.models.Photo;
import com.example.instaapp.models.User;
import com.example.instaapp.requests.RequestChangeName;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.LocalUser;
import com.example.instaapp.statik.NewPostFile;
import com.example.instaapp.viewModels.HomePageViewModel;
import com.example.instaapp.viewModels.ProfilePhotosViewModel;
import com.example.instaapp.views.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding userProfileBinding;
    private ProfilePhotosViewModel profilePhotosViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        userProfileBinding = FragmentProfileBinding.inflate(getLayoutInflater());

        profilePhotosViewModel = new ViewModelProvider(this).get(ProfilePhotosViewModel.class);

        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);

        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        staggeredGridLayoutManager.offsetChildrenVertical(16);

        userProfileBinding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        profilePhotosViewModel.setUp();

        profilePhotosViewModel.getObservedPhotos().observe(getViewLifecycleOwner(), s -> {
            HomePageAdapter adapter = new HomePageAdapter(profilePhotosViewModel.getObservedPhotos().getValue(), ((MainActivity)getActivity()));
            userProfileBinding.recyclerView.setAdapter(adapter);
        });

        ////////////////////////////////
        userProfileBinding.username.setText(LocalUser.getShowingName());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        userProfileBinding.pfp.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent,1000);
        });

        userProfileBinding.username.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Description");

            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(NewPostFile.description);
            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(IpConfig.getIp())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                                        ProfileApi profileApi = retrofit.create(ProfileApi.class);

                    Call<User> call = profileApi.changeName(new RequestChangeName(LocalUser.getName(),input.getText().toString()));
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Log.d("response", response.body().toString());
                            userProfileBinding.username.setText(input.getText().toString());
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("failure", t.toString());
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });


        Glide.with(getActivity())
                .load(IpConfig.getIp()+ "/api/profile/"+LocalUser.getName())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(userProfileBinding.pfp);

        return userProfileBinding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1000) {
            Uri selectedImage = data.getData();
            Bitmap bitmapImage = null;
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                File file = new File(String.valueOf(selectedImage));
                Log.d("FileName", file.getName());
                String name = file.getName().split("%")[file.getName().split("%").length-1];
                name = name.replace("2F","");
                Log.d("Name", name);
                String fileUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/" + name;
                Log.d("FILE_URI", fileUri);
                file = new File(fileUri);
                RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileRequest);
                RequestBody album = RequestBody.create(MultipartBody.FORM, LocalUser.getName());

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(IpConfig.getIp())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Log.d("XDDDDDDDDD", body.toString());
                ProfileApi profileApi = retrofit.create(ProfileApi.class);

                Call<User> call = profileApi.uploadFile(body, album);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("response", response.body().toString());
                        Glide.with(getActivity()).load(IpConfig.getIp()+ "/api/profile/"+LocalUser.getName())
                                .skipMemoryCache(true)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(userProfileBinding.pfp);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("failure", t.toString());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}