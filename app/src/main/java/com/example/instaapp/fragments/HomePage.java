package com.example.instaapp.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.instaapp.adapters.HomePageAdapter;
import com.example.instaapp.databinding.FragmentHomePageBinding;
import com.example.instaapp.viewModels.HomePageViewModel;
import com.example.instaapp.views.MainActivity;

public class HomePage extends Fragment {

    private HomePageViewModel homePageViewModel;
    private FragmentHomePageBinding homeBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        homeBinding = FragmentHomePageBinding.inflate(getLayoutInflater());
        homePageViewModel = new ViewModelProvider(this).get(HomePageViewModel.class);

        StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);

        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        staggeredGridLayoutManager.offsetChildrenVertical(16);


        homeBinding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
        homePageViewModel.setUp();
        homePageViewModel.getObservedPhotos().observe(getViewLifecycleOwner(), s -> {

            HomePageAdapter adapter = new HomePageAdapter(s, ((MainActivity)getActivity()));
            homeBinding.recyclerView.setAdapter(adapter);
        });

        return homeBinding.getRoot();
    }
}