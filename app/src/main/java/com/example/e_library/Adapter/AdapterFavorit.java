package com.example.e_library.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_library.Model.ModelBuku;
import com.example.e_library.Model.ModelFavorit;
import com.example.e_library.R;
import com.example.e_library.Utils.DataHelper;

import java.util.List;

public class AdapterFavorit extends RecyclerView.Adapter<AdapterFavorit.FavoritVierwHolder>{

    private List<ModelFavorit> modelFavoritList;
    Context context;
    FavoritListener favoritListener;
    BookmarksListener bookmarksListener;
    ViewPdfListener viewPdfListener;
    DataHelper dbHelper;

    public AdapterFavorit(Context context, List<ModelFavorit> modelFavoritList, FavoritListener favoritListener, BookmarksListener bookmarksListener, ViewPdfListener viewPdfListener) {
        this.modelFavoritList = modelFavoritList;
        this.context = context;
        this.favoritListener = favoritListener;
        this.bookmarksListener = bookmarksListener;
        this.viewPdfListener = viewPdfListener;
    }

    @NonNull
    @Override
    public FavoritVierwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_buku, parent, false);
        FavoritVierwHolder favoritVierwHolder = new FavoritVierwHolder(view);
        return favoritVierwHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritVierwHolder holder, int position) {
        dbHelper = new DataHelper(context);

        holder.tvJudul.setText(modelFavoritList.get(position).getJudul());
        holder.tvDeskripsi.setText(modelFavoritList.get(position).getDeskripsi());
        Glide.with(context).load(modelFavoritList.get(position).getCover()).into(holder.imgCover);

        holder.btnFav.setImageResource(R.drawable.ic_baseline_favorite_24);
        if(dbHelper.getIdCountBookmark(String.valueOf(modelFavoritList.get(position).getId())) > 0 ){
            holder.btnBM.setImageResource(R.drawable.ic_baseline_bookmark_24);
        }else {
            holder.btnBM.setImageResource(R.drawable.ic_baseline_bookmark_border_24);
        }

        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoritListener.favoritId(String.valueOf(modelFavoritList.get(position).getId()), modelFavoritList.get(position).getCover(), modelFavoritList.get(position).getContent(),modelFavoritList.get(position).getJudul(),modelFavoritList.get(position).getDeskripsi());
            }
        });
        holder.btnBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarksListener.bookmarksId(String.valueOf(modelFavoritList.get(position).getId()), modelFavoritList.get(position).getCover(), modelFavoritList.get(position).getContent(),modelFavoritList.get(position).getJudul(),modelFavoritList.get(position).getDeskripsi());
            }
        });
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPdfListener.pdfId(modelFavoritList.get(position).getContent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelFavoritList.size();
    }

    public class FavoritVierwHolder extends RecyclerView.ViewHolder {
        ImageView imgCover, btnFav, btnBM, btnView;
        TextView tvJudul, tvDeskripsi;
        public FavoritVierwHolder(@NonNull View itemView) {
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
        public void favoritId(String id, String cover, String content, String judul, String deskripsi);
    }

    public interface BookmarksListener{
        public void bookmarksId(String id, String cover, String content, String judul, String deskripsi);
    }

    public interface ViewPdfListener{
        public void pdfId(String content);
    }
}