package com.fayer.resepnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fayer.resepnew.adapter.RecylerViewAdapter;
import com.fayer.resepnew.controller.AddResepActivity;
import com.fayer.resepnew.helper.MyConstant;
import com.fayer.resepnew.model.ResepItem;
import com.fayer.resepnew.model.ResponseGetAllDataResep;
import com.fayer.resepnew.network.RestAPI;
import com.fayer.resepnew.network.RetroServer;
import com.fayer.resepnew.pref.SessionPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView listResep;
    RecyclerView.LayoutManager layoutManager;
    List<ResepItem> dataResep;
    FloatingActionButton addData;
    ProgressBar progressBar;
    SessionPref sessionPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionPref = new SessionPref(this);
        sessionPref.checkLoginMain();
       

        //inisialisasi
        progressBar = findViewById(R.id.progress_circular);
        listResep = findViewById(R.id.listResep);
        addData = findViewById(R.id.addData);

        layoutManager = new LinearLayoutManager(this);
        listResep.setLayoutManager(layoutManager);
        getDataResep();

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddResepActivity.class));
            }
        });

    }

    private void getDataResep() {
        progressBar.setVisibility(View.VISIBLE);
        //initial retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyConstant.urlResep)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

//        RestAPI api = retrofit.create(RestAPI.class);
        RestAPI api = RetroServer.getClient().create(RestAPI.class);
        //panggil data model
        Call<ResponseGetAllDataResep> modelResepCall = api.getDataResep();
        modelResepCall.enqueue(new Callback<ResponseGetAllDataResep>() {
            @Override
            public void onResponse(@NonNull Call<ResponseGetAllDataResep> call, @NonNull Response<ResponseGetAllDataResep> response) {
                assert response.body() != null;
//                Log.e("TAG","Data Berhasil"+ new Gson().toJson(response.body().getResep()));

//                String pesan = response.body().getPesan();
                String sukses = response.body().getSukses();

                if (sukses.equals("true")){
                    progressBar.setVisibility(View.GONE);
                    dataResep = response.body().getResep();
                    tampilResep();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,"Data Tidak di temukan",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseGetAllDataResep> call, Throwable t) {
                Log.e("TAG","Error Message"+t.getMessage());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd = item.getItemId();
        if (idd == R.id.m_logout){
            sessionPref.logoutUser();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void tampilResep() {
        String itemData[] = new String[dataResep.size()];
        for (int i = 0; i < dataResep.size(); i++) {
            itemData[i] = dataResep.get(i).getNamaResep();
        }

        RecylerViewAdapter adapter = new RecylerViewAdapter(this,dataResep);
        listResep.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}