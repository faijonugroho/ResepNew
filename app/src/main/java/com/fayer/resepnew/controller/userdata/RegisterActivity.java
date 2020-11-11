package com.fayer.resepnew.controller.userdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fayer.resepnew.MainActivity;
import com.fayer.resepnew.R;
import com.fayer.resepnew.helper.FormValidation;
import com.fayer.resepnew.model.ResponseData;
import com.fayer.resepnew.network.RestAPI;
import com.fayer.resepnew.network.RetroServer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText nama, jk, image, email, username, password;
    Button btnRegister;
    TextView tvGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nama = findViewById(R.id.edt_nama_user);
        jk = findViewById(R.id.edt_jk);
        image = findViewById(R.id.edt_image_user);
        email = findViewById(R.id.edt_email_user);
        username = findViewById(R.id.edt_username);
        password = findViewById(R.id.edt_password);

        btnRegister = findViewById(R.id.btn_register);
        tvGoLogin = findViewById(R.id.tv_go_login);

        //method proses register data
        registerData();

        tvGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                onBackPressed();
            }
        });


    }

    private void registerData() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaVal = nama.getText().toString();
                String jkVal = jk.getText().toString();
                String imageVal = image.getText().toString();
                String emailVal = email.getText().toString();
                String usernameVal = username.getText().toString();
                String passwordVal = password.getText().toString();

//                new FormValidation(nama,"Nama Pengguna","isEmpty");

                String[] formInput = {
                                        String.valueOf(isEmptyInput(nama)),
                                        String.valueOf(valToString(jk).isEmpty()),
                                        String.valueOf(valToString(image).isEmpty()),
                                        String.valueOf(valToString(email).trim().isEmpty()),
                                        String.valueOf(valToString(username).isEmpty()),
                                        String.valueOf(valToString(password).isEmpty())
                                    };
                if (Arrays.asList(formInput).contains("true")){
                    if (isEmptyInput(nama)){
                        nama.setError("Nama Pengguna harus di isi.");
                    }
                    if (isEmptyInput(jk)){
                        jk.setError("Jenis Kelamin harus di isi");
                    }
                    if (isEmptyInput(image)){
                        image.setError("Photo harus di isi");
                    }
                    if (isEmptyInput(email,true)){
                        email.setError("Email harus di isi");
                    }
                    if (isEmptyInput(username)){
                        username.setError("Username harus di isi");
                    }
                    if (isEmptyInput(password)){
                        password.setError("Jenis Kelamin harus di isi");
                    }

                    Toast.makeText(RegisterActivity.this,"data tidak boleh kosong",Toast.LENGTH_SHORT).show();
                } else {
                    RestAPI apiUser = RetroServer.getClient().create(RestAPI.class);
                    Call<ResponseData> registerInsert = apiUser.userRegister(namaVal, jkVal, imageVal,emailVal,usernameVal,passwordVal);
//                    Log.e("apiUser"+ new Gson().toJson(registerInsert.enqueue()));

                    registerInsert.enqueue(new Callback<ResponseData>() {
                        @Override
                        public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {

                            try {
                                assert response.body() != null;
                                int kode = response.body().getKode();
                                if (kode == 1){
                                    Toast.makeText(RegisterActivity.this,"Berhasil Registrasi akun.",Toast.LENGTH_SHORT).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            finish();
                                        }
                                    },2000);
                                } else {
                                    Toast.makeText(RegisterActivity.this,"Gagal Registrasi akun.",Toast.LENGTH_SHORT).show();
                                }
                            } catch (NullPointerException e) {
                                Toast.makeText(RegisterActivity.this,"Gagal Registrasi akun."+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {
                            Toast.makeText(RegisterActivity.this,"Gagal Registrasi akun."+t.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }

    public String valToString(EditText input){
        return input.getText().toString();
    }

    private Boolean isEmptyInput(EditText input){
        return input.getText().toString().isEmpty();
    }

    private Boolean isEmptyInput(EditText input,Boolean trimVal){
        if (trimVal){
            return input.getText().toString().trim().isEmpty();
        } else {
            return input.getText().toString().isEmpty();
        }
    }

}