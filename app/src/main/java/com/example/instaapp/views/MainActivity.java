package com.example.instaapp.views;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.instaapp.R;
import com.example.instaapp.api.UsersApi;
import com.example.instaapp.data.Auth;
import com.example.instaapp.data.IpConfig;
import com.example.instaapp.data.LocalUser;
import com.example.instaapp.databinding.ActivityMainBinding;

import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        ActivityMainBinding mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        readSharedPref();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersApi usersApi = retrofit.create(UsersApi.class);

        if (LocalUser.getToken() != "" && LocalUser.getUsername() != "") {

            Call<Auth> call = usersApi.postAuthData("Bearer " + LocalUser.getToken());
            call.enqueue(new Callback<Auth>() {
                @Override
                public void onResponse(Call<Auth> call, Response<Auth> response) {
                    if(!response.body().isSuccess()){
                        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Auth> call, Throwable t) {
                    Log.d("Login", t.toString());
                }
            });
        }else if(LocalUser.getToken() == "" && LocalUser.getUsername() == ""){
            logout();
        }

        AtomicReference<MenuItem> previouslySelected = new AtomicReference<>();

        mainBinding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        mainBinding.sideMenu.setNavigationItemSelectedListener(item-> {
            mainBinding.drawer.closeDrawer(GravityCompat.END);
            int id = item.getItemId();
            Log.d("Mess", String.valueOf(id));
            switch(id){
                case R.id.settings:

                    break;
                case R.id.changeIP:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Change ip");

                    final EditText input = new EditText(this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    input.setText(IpConfig.getRawIp());
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            IpConfig.setIp(input.getText().toString());
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {}
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
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
        LocalUser.setUsername(sharedPreferences.getString("username", ""));
    }
    private void logout(){
        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }
}