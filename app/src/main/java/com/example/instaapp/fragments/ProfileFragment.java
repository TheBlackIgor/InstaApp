package com.example.instaapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.instaapp.R;
import com.example.instaapp.adapters.AdapterProfilePics;
import com.example.instaapp.adapters.HomePageAdapter;
import com.example.instaapp.databinding.FragmentHomePageBinding;
import com.example.instaapp.databinding.FragmentProfileBinding;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.LocalUser;
import com.example.instaapp.viewModels.HomePageViewModel;
import com.example.instaapp.viewModels.ProfilePhotosViewModel;
import com.example.instaapp.views.MainActivity;

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

//        UsersAPI usersAPI = retrofit.create(UsersAPI.class);
//
//        Call<Bio> call = usersAPI.getBioByName(username);
//
//        call.enqueue(new Callback<Bio>() {
//            @Override
//            public void onResponse(Call<Bio> call, Response<Bio> response) {
//                userProfileBinding.bio.setText(response.body().getBio());
//            }
//
//            @Override
//            public void onFailure(Call<Bio> call, Throwable t) {
//                Log.d("logdev", t.toString());
//            }
//        });



        Glide.with(getActivity()).load(IpConfig.getIp()+ "/api/user/"+LocalUser.getShowingName())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(userProfileBinding.pfp);

        return userProfileBinding.getRoot();
    }
}