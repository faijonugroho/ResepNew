package com.fayer.resepnew.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.fayer.resepnew.MainActivity;
import com.fayer.resepnew.R;
import com.fayer.resepnew.model.ResepItem;
import com.fayer.resepnew.model.ResponseData;
import com.fayer.resepnew.network.RestAPI;
import com.fayer.resepnew.network.RetroServer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddResepActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar pd;
    Button btnInsertdata;
    Button btntampildata;
    Button btnUpdate;
    Button btnhapus;
    EditText edtNama;
    EditText edtGambar;
    EditText edtResep;
    String idData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resep);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        btnhapus = findViewById(R.id.btnhapus);
        btnInsertdata = findViewById(R.id.btn_insertdata);
        btntampildata = findViewById(R.id.btntampildata);
        btnUpdate = findViewById(R.id.btnUpdate);
        edtGambar = findViewById(R.id.edt_gambar);
        edtNama = findViewById(R.id.edt_nama);
        edtResep = findViewById(R.id.edtResep);
        pd = findViewById(R.id.pd);

        // for update/delete data, from extra Intent DetailResepActivity
        Intent getData = getIntent();
        idData = getData.getStringExtra("ID");
        if (idData != null) {
            setTitle("Update Data Resep");

            btnInsertdata.setVisibility(View.GONE);
            btntampildata.setVisibility(View.GONE);
            btnhapus.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            edtNama.setText(getData.getStringExtra("NAMA"));
            edtGambar.setText(getData.getStringExtra("GAMBAR"));
            edtResep.setText(getData.getStringExtra("DETAIL"));
        } else {
            setTitle("Tambah Data Resep");
        }

        btnUpdate.setOnClickListener(this);
        btntampildata.setOnClickListener(this);
        btnInsertdata.setOnClickListener(this);
        btnhapus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btntampildata) {
            startActivity(new Intent(AddResepActivity.this, MainActivity.class));
            finish();
        } else if (view.getId() == R.id.btn_insertdata) {
            String snama = edtNama.getText().toString();
            String sgambar = edtGambar.getText().toString();
            String sresep = edtResep.getText().toString();
            if (snama.isEmpty()) {
                edtNama.setError("nama perlu di isi");
            } else if (sgambar.isEmpty()) {
                edtGambar.setError("gambar perlu di isi");
            } else if (sresep.isEmpty()) {
                edtResep.setError("detail resep perlu di isi");
            } else {
                pd.setVisibility(View.VISIBLE);
                RestAPI api = RetroServer.getClient().create(RestAPI.class);
                Call<ResponseData> insertResep = api.insertData(snama, sresep, sgambar);
                insertResep.enqueue(new Callback<ResponseData>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {
                        try {
                            assert response.body() != null;
                            int kode = response.body().getKode();
                            if (String.valueOf(kode).equals("1")) {
                                pd.setVisibility(View.GONE);
                                Toast.makeText(AddResepActivity.this, "Berhasil simpan", Toast.LENGTH_SHORT).show();
                                edtGambar.setText("");
                                edtNama.setText("");
                                edtResep.setText("");
                                startActivity(new Intent(AddResepActivity.this, MainActivity.class));
                                finish();
                            } else {
                                pd.setVisibility(View.GONE);
                                Toast.makeText(AddResepActivity.this, "data error, gagal simpan", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NullPointerException e) {
                            Toast.makeText(AddResepActivity.this, "errror" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {
                        pd.setVisibility(View.GONE);
                        Toast.makeText(AddResepActivity.this, "data error, gagal simpan", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (view.getId() == R.id.btnUpdate) {
            RestAPI api = RetroServer.getClient().create(RestAPI.class);
            Call<ResponseData> updateData = api.updateData(
                    idData,
                    edtNama.getText().toString(),
                    edtResep.getText().toString(),
                    edtGambar.getText().toString()
            );
            updateData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {
                    Toast.makeText(AddResepActivity.this,"Berhasil Update Data resep",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(AddResepActivity.this,MainActivity.class));
                            finish();
                        }
                    },2000);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {
                    Toast.makeText(AddResepActivity.this,"Gagal, update data resep "+t.getMessage(),Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(AddResepActivity.this, MainActivity.class));
                            finish();
                        }
                    }, 2000);
                }
            });
//            Toast.makeText(this, "Masih Dalam pengembangan.", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.btnhapus) {
            RestAPI apidata = RetroServer.getClient().create(RestAPI.class);
            Call<ResponseData> del = apidata.deleteData(idData);
            del.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {
                    Toast.makeText(AddResepActivity.this,"Berhasil Hapus Data Resep.",Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(AddResepActivity.this,MainActivity.class));
                            finish();
                        }
                    },2000);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {
                    Toast.makeText(AddResepActivity.this,"Gagal Hapus Data Resep.",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(AddResepActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);
                }
            });


//            Toast.makeText(this,"Tombol Hapus sedang dalam proses",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}