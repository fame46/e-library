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
import com.example.e_library.Model.ModelBokkmark;
import com.example.e_library.Model.ModelFavorit;
import com.example.e_library.R;
import com.example.e_library.Utils.DataHelper;

import java.util.List;

public class AdapterBookmark extends RecyclerView.Adapter<AdapterBookmark.BookmarkVierwHolder>{

    private List<ModelBokkmark> modelBokkmarkList;
    Context context;
    FavoritListener favoritListener;
    BookmarksListener bookmarksListener;
    ViewPdfListener viewPdfListener;
    DataHelper dbHelper;

    public AdapterBookmark(Context context, List<ModelBokkmark> modelBokkmarkList, FavoritListener favoritListener, BookmarksListener bookmarksListener, ViewPdfListener viewPdfListener) {
        this.modelBokkmarkList = modelBokkmarkList;
        this.context = context;
        this.favoritListener = favoritListener;
        this.bookmarksListener = bookmarksListener;
        this.viewPdfListener = viewPdfListener;
    }

    @NonNull
    @Override
    public BookmarkVierwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_buku, parent, false);
        BookmarkVierwHolder bookmarkVierwHolder = new BookmarkVierwHolder(view);
        return bookmarkVierwHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterBookmark.BookmarkVierwHolder holder, int position) {
        dbHelper = new DataHelper(context);

        if(dbHelper.getIdCount(String.valueOf(modelBokkmarkList.get(position).getId())) > 0 ){
            holder.btnFav.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else {
            holder.btnFav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

        holder.btnBM.setImageResource(R.drawable.ic_baseline_bookmark_24);

        holder.tvJudul.setText(modelBokkmarkList.get(position).getJudul());
        holder.tvDeskripsi.setText(modelBokkmarkList.get(position).getDeskripsi());
        Glide.with(context).load(modelBokkmarkList.get(position).getCover()).into(holder.imgCover);
        holder.btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoritListener.favoritId(String.valueOf(modelBokkmarkList.get(position).getId()), modelBokkmarkList.get(position).getCover(), modelBokkmarkList.get(position).getContent(),modelBokkmarkList.get(position).getJudul(),modelBokkmarkList.get(position).getDeskripsi(), String.valueOf(modelBokkmarkList.get(position).getTahun()), modelBokkmarkList.get(position).getPengarang(),modelBokkmarkList.get(position).getPenerbit());
            }
        });
        holder.btnBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookmarksListener.bookmarksId(String.valueOf(modelBokkmarkList.get(position).getId()), modelBokkmarkList.get(position).getCover(), modelBokkmarkList.get(position).getContent(),modelBokkmarkList.get(position).getJudul(),modelBokkmarkList.get(position).getDeskripsi(), String.valueOf(modelBokkmarkList.get(position).getTahun()), modelBokkmarkList.get(position).getPengarang(),modelBokkmarkList.get(position).getPenerbit());
            }
        });
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPdfListener.pdfId(String.valueOf(modelBokkmarkList.get(position).getId()), modelBokkmarkList.get(position).getCover(), modelBokkmarkList.get(position).getContent(),modelBokkmarkList.get(position).getJudul(),modelBokkmarkList.get(position).getDeskripsi(), String.valueOf(modelBokkmarkList.get(position).getTahun()), modelBokkmarkList.get(position).getPengarang(),modelBokkmarkList.get(position).getPenerbit());
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelBokkmarkList.size();
    }

    public class BookmarkVierwHolder extends RecyclerView.ViewHolder {
        ImageView imgCover, btnFav, btnBM, btnView;
        TextView tvJudul, tvDeskripsi;
        public BookmarkVierwHolder(@NonNull View itemView) {
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
