package com.fayer.resepnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fayer.resepnew.R;
import com.fayer.resepnew.controller.DetailResepActivity;
import com.fayer.resepnew.helper.MyConstant;
import com.fayer.resepnew.model.ResepItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.MyHolder> {

    Context context;
    List<ResepItem> data_resep;
    //buat constructor dari variable di atas
    public RecylerViewAdapter(Context con, List<ResepItem> dataResep){
        this.context = con;
        this.data_resep = dataResep;
    }

    @NonNull
    @Override
    public RecylerViewAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampilan_nama_resep,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewAdapter.MyHolder holder, final int position) {
        holder.txtNama.setText(data_resep.get(position).getNamaResep());
        Picasso.with(context).load(MyConstant.urlResep +data_resep.get(position).getGambar())
                .error(R.drawable.image_thumbnail).into(holder.imgMakanan);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent kirim = new Intent(context, DetailResepActivity.class);

                kirim.putExtra("id",data_resep.get(position).getIdResep());
                kirim.putExtra("nama",data_resep.get(position).getNamaResep());
                kirim.putExtra("gambar",data_resep.get(position).getGambar());
                kirim.putExtra("detail",data_resep.get(position).getDetail());
                context.startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_resep.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        ImageView imgMakanan;
        TextView txtNama;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgMakanan = itemView.findViewById(R.id.imgmakanan);
            txtNama = itemView.findViewById(R.id.txtnama);
        }
    }
}
