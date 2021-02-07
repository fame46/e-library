package com.example.e_library.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_library.Model.ModelKategori;
import com.example.e_library.R;

import java.util.List;

public class AdapterKategori extends RecyclerView.Adapter<AdapterKategori.KategoriAdminVierwHolder>{

    private List<ModelKategori> modelKategoriList;

    public AdapterKategori(List<ModelKategori> modelKategoriList) {
        this.modelKategoriList = modelKategoriList;
    }

    @NonNull
    @Override
    public AdapterKategori.KategoriAdminVierwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kategori, parent, false);
        AdapterKategori.KategoriAdminVierwHolder kategoriAdminVierwHolder = new AdapterKategori.KategoriAdminVierwHolder(view);
        return kategoriAdminVierwHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKategori.KategoriAdminVierwHolder holder, int position) {
        holder.tvJudul.setText(modelKategoriList.get(position).getNama_kategori());
        holder.tvDeskripsi.setText(modelKategoriList.get(position).getDeskripsi());

    }

    @Override
    public int getItemCount() {
        return modelKategoriList.size();
    }

    public class KategoriAdminVierwHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvDeskripsi;
        public KategoriAdminVierwHolder(@NonNull View itemView) {
            super(itemView);

            tvJudul = itemView.findViewById(R.id.tvJudulKategori);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsiKategori);
        }
    }
}
