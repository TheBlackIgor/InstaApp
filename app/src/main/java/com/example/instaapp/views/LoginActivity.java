package com.example.instaapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.instaapp.api.UsersApi;
import com.example.instaapp.statik.IpConfig;
import com.example.instaapp.statik.LocalUser;
import com.example.instaapp.databinding.ActivityLoginBinding;
import com.example.instaapp.requests.LoginRequest;
import com.example.instaapp.responses.LoginResponse;
import com.example.instaapp.utils.Dialogs;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLoginBinding loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersApi usersApi = retrofit.create(UsersApi.class);

        loginBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        loginBinding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.changeIp(LoginActivity.this);
            }
        });

        loginBinding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usernameInput = loginBinding.usernameInput;
                TextView passwordInput = loginBinding.passwordInput;
                if(passwordInput.getText().length() == 0 || usernameInput.getText().length() == 0){
                    Snackbar s = Snackbar.make(view, "You need to fill fields", Snackbar.LENGTH_SHORT);
                    s.show();
                }else if(usernameInput.getText().length() < 4){
                    Snackbar s = Snackbar.make(view, "Username is to short", Snackbar.LENGTH_SHORT);
                    s.show();
                }else if(passwordInput.getText().length() < 4){
                    Snackbar s = Snackbar.make(view, "Password is to short", Snackbar.LENGTH_SHORT);
                    s.show();
                }else {
                    Call<LoginResponse> call = usersApi.login(new LoginRequest(
                            loginBinding.usernameInput.getText().toString(),
                            loginBinding.passwordInput.getText().toString())
                           );
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            Log.d("body", response.body().toString());
                            if (response.body().isSuccess()) {
                                SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", response.body().getName());
                                editor.putString("showingName", response.body().getShowingName());
                                Log.d("SHOWINGNAME", response.body().getShowingName());
                                editor.putString("token", response.body().getToken());

                                editor.apply();
                                LocalUser.setName(response.body().getName());
                                LocalUser.setToken(response.body().getToken());
                                LocalUser.setShowingName(response.body().getShowingName());

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar s = Snackbar.make(view, "You sended wrong data", Snackbar.LENGTH_SHORT);
                                s.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.d("failure", t.toString());
                        }
                    });
                }
            };
        });


    }
}