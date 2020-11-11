package com.fayer.resepnew.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.fayer.resepnew.MainActivity;
import com.fayer.resepnew.R;
import com.fayer.resepnew.helper.MyConstant;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailResepActivity extends AppCompatActivity {

    ImageView imgMakanan;
    TextView txtNama, txtDetail;
    String id,nama,detail,gambar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resep);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        setTitle("Detail Resep");

        imgMakanan = findViewById(R.id.imgmakanan);
        txtNama = findViewById(R.id.txtnama);
        txtDetail = findViewById(R.id.txtdetail);

        //extra data from RecylerViewAdapter itemView
        id = getIntent().getStringExtra("id");
        nama = getIntent().getStringExtra("nama");
        detail = getIntent().getStringExtra("detail");
        gambar = getIntent().getStringExtra("gambar");

        txtNama.setText(nama);
        txtDetail.setText(detail);

        Picasso.with(context).load(MyConstant.urlResep+gambar).error(R.drawable.image_thumbnail).into(imgMakanan);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resep, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idd = item.getItemId();

        if (idd == android.R.id.home) {
            finish();
        } else if (idd==R.id.idUpdateDelete){
            Intent intent = new Intent(this, AddResepActivity.class);
            intent.putExtra("ID",id);
            intent.putExtra("NAMA",nama);
            intent.putExtra("DETAIL",detail);
            intent.putExtra("GAMBAR",gambar);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}