package com.fayer.resepnew.controller.userdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fayer.resepnew.MainActivity;
import com.fayer.resepnew.R;
import com.fayer.resepnew.model.ResponseData;
import com.fayer.resepnew.network.RestAPI;
import com.fayer.resepnew.network.RetroServer;
import com.fayer.resepnew.pref.SessionPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView tvRegister;
    ProgressBar pdLogin;
    SessionPref sessionPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionPref = new SessionPref(this);
        sessionPref.checkLogin();

        edtEmail = findViewById(R.id.edt_login_email);
        edtPassword = findViewById(R.id.edt_login_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register_now);
        pdLogin = findViewById(R.id.pd_login);

        registerNow();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAction();
            }
        });

    }

    private void loginAction() {
        final String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();

        if (email.isEmpty() || pass.isEmpty()){
            if (email.isEmpty()){
                edtEmail.setError("Email harus di isi");
            }
            if (pass.isEmpty()){
                edtPassword.setError("Password harus di isi");
            }
            Toast.makeText(this,"Data wajib di isi", Toast.LENGTH_SHORT).show();
        } else {
            pdLogin.setVisibility(View.VISIBLE);

            RestAPI apiUser = RetroServer.getClient().create(RestAPI.class);
            Call<ResponseData> loginUser = apiUser.userLogin(email,pass);
            loginUser.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {
                    assert response.body() != null;
                    int kode = response.body().getKode();
                    pdLogin.setVisibility(View.GONE);
                    if (kode == 1){
                        sessionPref.createLoginSession(email);
                        Toast.makeText(LoginActivity.this,"Berhasil Login.",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }
                        }, 2000);
                    } else {
                        Toast.makeText(LoginActivity.this,"Gagal Login.",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {
                    pdLogin.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Gagal Login.",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void registerNow() {
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}