package com.example.instaapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instaapp.R;
import com.example.instaapp.api.UsersApi;
import com.example.instaapp.data.IpConfig;
import com.example.instaapp.data.LocalUser;
import com.example.instaapp.databinding.ActivityLoginBinding;
import com.example.instaapp.databinding.ActivityRegisterBinding;
import com.example.instaapp.requests.LoginRequest;
import com.example.instaapp.requests.RegisterRequest;
import com.example.instaapp.responses.LoginResponse;
import com.example.instaapp.responses.RegisterResponse;
import com.example.instaapp.utils.Dialogs;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = registerBinding.getRoot();
        setContentView(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(IpConfig.getIp())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersApi usersApi = retrofit.create(UsersApi.class);

        registerBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        });
        registerBinding.settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialogs.changeIp(RegisterActivity.this);
            }
        });

        registerBinding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView usernameInput = registerBinding.usernameInput;
                TextView passwordInput = registerBinding.passwordInput;
                TextView emailInput = registerBinding.emailInput;
                if(passwordInput.getText().length() == 0 || usernameInput.getText().length() == 0 || emailInput.getText().length() == 0){
                    Snackbar s = Snackbar.make(view, "You need to fill fields", Snackbar.LENGTH_SHORT);
                    s.show();
                }else if(usernameInput.getText().length() < 4){
                    Snackbar s = Snackbar.make(view, "Username is to short", Snackbar.LENGTH_SHORT);
                    s.show();
                }else if(passwordInput.getText().length() < 4) {
                    Snackbar s = Snackbar.make(view, "Password is to short", Snackbar.LENGTH_SHORT);
                    s.show();
                }
                    else if(!emailInput.getText().toString().contains("@") || !emailInput.getText().toString().contains(".")) {
                    Snackbar s = Snackbar.make(view, "Wrong email", Snackbar.LENGTH_SHORT);
                    s.show();
                }else {
                    Call<RegisterResponse> call = usersApi.register(new RegisterRequest(
                            registerBinding.usernameInput.getText().toString(),
                            registerBinding.emailInput.getText().toString(),
                            registerBinding.passwordInput.getText().toString())
                    );
                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                            if(response.body().getToken().contains("taken")){
                                if(response.body().getToken().contains("email")){
                                    Snackbar s = Snackbar.make(view, "Email is taken", Snackbar.LENGTH_SHORT);
                                    s.show();
                                }else{
                                    Snackbar s = Snackbar.make(view, "Username is taken", Snackbar.LENGTH_SHORT);
                                    s.show();
                                }
                            }
                            else if (response.body().getToken().contains("confirm")) {
                                registerBinding.confirmTextView.setText(IpConfig.getIp()+response.body().getToken());

//                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
                            } else {
                                Snackbar s = Snackbar.make(view, "You sended wrong data", Snackbar.LENGTH_SHORT);
                                s.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            Log.d("failure", t.toString());
                        }
                    });
                }
            };
        });
    }
}