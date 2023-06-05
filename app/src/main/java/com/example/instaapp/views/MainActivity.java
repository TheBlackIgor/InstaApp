package com.example.instaapp.views;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.instaapp.fragments.AddPost;
import com.example.instaapp.R;
import com.example.instaapp.api.UsersApi;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.LocalUser;
import com.example.instaapp.databinding.ActivityMainBinding;
import com.example.instaapp.fragments.HomePage;
import com.example.instaapp.fragments.Post;
import com.example.instaapp.responses.ResponseAuth;
import com.example.instaapp.utils.Dialogs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    HomePage homePageFragment;
    Post postFragment;
    AddPost addPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        readSharedPref();


        homePageFragment = new HomePage();
        postFragment = new Post();
        addPost = new AddPost();

        replaceFragment(homePageFragment, "home");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersApi usersApi = retrofit.create(UsersApi.class);

        if (LocalUser.getToken() != "" && LocalUser.getName() != "") {
            Call<ResponseAuth> call = usersApi.postAuthData("Bearer " + LocalUser.getToken());
            call.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    Log.d("res", response.body().toString());
                    if(!response.body().isSuccess()){
                        logout();
                    }
                }
                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Log.d("Login", t.toString());
                }
            });
        }else if(LocalUser.getToken() == "" && LocalUser.getName() == ""){
            logout();
        }else{
            logout();
        }

        mainBinding.bottomMenu.setOnItemSelectedListener(item-> {
            switch(item.getItemId()){
                case R.id.home:
                    replaceFragment(homePageFragment, "home");
                    break;
                case R.id.addPost:
                    replaceFragment(addPost, "addPost");
                    break;
                case R.id.search:

                    break;
            }

            return true;
        });

        mainBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mainBinding.sideMenu.setNavigationItemSelectedListener(item-> {
            mainBinding.drawer.closeDrawer(GravityCompat.END);
            switch(item.getItemId()){
                case R.id.settings:

                    break;
                case R.id.changeIP:
                    Dialogs.changeIp(this);
                    break;
                case R.id.logout:
                    SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", "");
                    editor.putString("token", "");
                    editor.apply();
                    logout();
                    break;
            }

            return true;
        });

        //OPEN DRAWER ON SETTINGS CLICK
        mainBinding.settings.setOnClickListener(view->{
            mainBinding.drawer.openDrawer(GravityCompat.END);
        });
    }
    public void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, fragment, tag)
                .commit();
    }
    private void readSharedPref(){
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        LocalUser.setToken(sharedPreferences.getString("token", ""));
        LocalUser.setName(sharedPreferences.getString("name", ""));
    }
    private void logout(){
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }
    public void showPost(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.root, postFragment, "post")
                .commit();
    }
}