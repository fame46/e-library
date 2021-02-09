package com.example.e_library.Adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_library.Model.ModelBuku;
import com.example.e_library.Model.ModelKategori;
import com.example.e_library.R;
import com.example.e_library.Utils.DataHelper;

import java.util.List;

public class AdapterBuku extends RecyclerView.Adapter<AdapterBuku.BukuVierwHolder>{

    private List<ModelBuku> modelBukuList;
    Context context;
    FavoritListener favoritListener;
    BookmarksListener bookmarksListener;
    ViewPdfListener viewPdfListener;
    DataHelper dbHelper;


    public AdapterBuku(Context context,List<ModelBuku> modelBukuList, FavoritListener favoritListener, BookmarksListener bookmarksListener, ViewPdfListener viewPdfListener) {
        this.modelBukuList = modelBukuList;
        this.context = context;
        this.favoritListener = favoritListener;
        this.bookmarksListener = bookmarksListener;
        this.viewPdfListener = viewPdfListener;
    }

    @NonNull
    @Override
    public AdapterBuku.BukuVierwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_buku, parent, false);
        AdapterBuku.BukuVierwHolder bukuVierwHolder = new AdapterBuku.BukuVierwHolder(view);
        return bukuVierwHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBuku.BukuVierwHolder holder, int position) {
        dbHelper = new DataHelper(context);
        if(dbHelper.getIdCount(String.valueOf(modelBukuList.get(position).getId())) > 0 ){
            holder.btnFav.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else {
            holder.btnFav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        if(dbHelper.getIdCountBookmark(String.valueOf(modelBukuList.get(position).getId())) > 0 ){
            holder.btnBM.setImageResource(R.drawable.ic_baseline_bookmark_24);
        }else {
            holder.btnBM.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
        }

        holder.tvJudul.setText(modelBukuList.get(position).getJudul());
        holder.tvDeskripsi.setText(Html.fromHtml(modelBukuList.get(position).getDeskripsi()).toString());
        Glide.with(context).load(modelBukuList.get(position).getCover()).into(holder.imgCover);
        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoritListener.favoritId(String.valueOf(modelBukuList.get(position).getId()), modelBukuList.get(position).getCover(), modelBukuList.get(position).getContent(),modelBukuList.get(position).getJudul(),modelBukuList.get(position).getDeskripsi(), String.valueOf(modelBukuList.get(position).getTahun()), modelBukuList.get(position).getPengarang(),modelBukuList.get(position).getPenerbit());
            }
        });
        holder.btnBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarksListener.bookmarksId(String.valueOf(modelBukuList.get(position).getId()), modelBukuList.get(position).getCover(), modelBukuList.get(position).getContent(),modelBukuList.get(position).getJudul(),modelBukuList.get(position).getDeskripsi(), String.valueOf(modelBukuList.get(position).getTahun()), modelBukuList.get(position).getPengarang(),modelBukuList.get(position).getPenerbit());
            }
        });
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPdfListener.pdfId(String.valueOf(modelBukuList.get(position).getId()), modelBukuList.get(position).getCover(), modelBukuList.get(position).getContent(),modelBukuList.get(position).getJudul(),modelBukuList.get(position).getDeskripsi(), String.valueOf(modelBukuList.get(position).getTahun()), modelBukuList.get(position).getPengarang(),modelBukuList.get(position).getPenerbit());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelBukuList.size();
    }

    public class BukuVierwHolder extends RecyclerView.ViewHolder {
        ImageView imgCover, btnFav, btnBM, btnView;
        TextView tvJudul, tvDeskripsi;
        public BukuVierwHolder(@NonNull View itemView) {
            super(itemView);
            imgCover = itemView.findViewById(R.id.imageViewSampul);
            btnFav = itemView.findViewById(R.id.imageViewFovorit);
            btnBM = itemView.findViewById(R.id.imageViewBookmark);
            btnView = itemView.findViewById(R.id.imageViewViewPdf);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvDeskripsi = itemView.findViewById(R.id.tvDeskripsi);

        }
    }

    public interface FavoritListener{
        public void favoritId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit);
    }

    public interface BookmarksListener{
        public void bookmarksId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit);
    }

    public interface ViewPdfListener{
        public void pdfId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit);
    }
}
