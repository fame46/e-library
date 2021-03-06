package com.example.e_library.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.e_library.Adapter.AdapterBookmark;
import com.example.e_library.Adapter.AdapterFavorit;
import com.example.e_library.DetailBukuActivity;
import com.example.e_library.Model.ModelBokkmark;
import com.example.e_library.Model.ModelFavorit;
import com.example.e_library.PdfViewActivity;
import com.example.e_library.R;
import com.example.e_library.Utils.DataHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends Fragment implements AdapterBookmark.FavoritListener, AdapterBookmark.BookmarksListener, AdapterBookmark.ViewPdfListener {

    List<ModelBokkmark> modelBokkmarkList = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    DividerItemDecoration did;
    RecyclerView.Adapter adapter;

    DataHelper dbcenter;

    ProgressBar loading;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bookmarks, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewBookmark);
        loading = v.findViewById(R.id.loadingFavorit);

        getData();

        MobileAds.initialize(getContext(), "ca-app-pub-2707736392233075/5248913902");
        adView = (AdView)v.findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void getDataCari(String judul){
        dbcenter = new DataHelper(getContext());
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        modelBokkmarkList = dbcenter.getBookmarkListCari(judul);

        adapter = new AdapterBookmark(getContext(),modelBokkmarkList,BookmarksFragment.this,BookmarksFragment.this, BookmarksFragment.this);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(did);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);
                    getDataCari(newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    getDataCari(query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    public void getData(){
        dbcenter = new DataHelper(getContext());
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        modelBokkmarkList = dbcenter.getBookmarkList();

        adapter = new AdapterBookmark(getContext(),modelBokkmarkList,BookmarksFragment.this,BookmarksFragment.this, BookmarksFragment.this);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(did);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void favoritId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit) {
        dbcenter = new DataHelper(getContext());
        if(dbcenter.getIdCount(id) > 0){
            dbcenter.hapusDataFavorit(id);
            getData();
            Toast.makeText(getContext(), "dihapus dari favorit", Toast.LENGTH_LONG).show();
        }else {
            SQLiteDatabase db = dbcenter.getWritableDatabase();
            db.execSQL("insert into favorit(id, cover, content, judul, deskripsi, tahun, pengarang, penerbit) values('" +
                    id + "','" +
                    cover + "','" +
                    content + "','" +
                    judul + "','" +
                    deskripsi + "','" +
                    tahun + "','" +
                    pengarang + "','" +
                    penerbit + "')");
            getData();
            Toast.makeText(getContext(), "Ditambahkan ke favorit", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void bookmarksId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit) {
        dbcenter = new DataHelper(getContext());
        if(dbcenter.getIdCountBookmark(id) > 0){
            dbcenter.hapusDataBookmark(id);
            getData();
            Toast.makeText(getContext(), "dihapus dari bookmark", Toast.LENGTH_LONG).show();
        }else {
            SQLiteDatabase db = dbcenter.getWritableDatabase();
            db.execSQL("insert into bookmarks(id, cover, content, judul, deskripsi, tahun, pengarang, penerbit) values('" +
                    id + "','" +
                    cover + "','" +
                    content + "','" +
                    judul + "','" +
                    deskripsi + "','" +
                    tahun + "','" +
                    pengarang + "','" +
                    penerbit + "')");
            getData();
            Toast.makeText(getContext(), "Ditambahkan ke bookmarks", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void pdfId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit) {
        Intent intent = new Intent(getContext(), DetailBukuActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("cover", cover);
        intent.putExtra("content", content);
        intent.putExtra("judul", judul);
        intent.putExtra("deskripsi", deskripsi);
        intent.putExtra("tahun", tahun);
        intent.putExtra("pengarang", pengarang);
        intent.putExtra("penerbit", penerbit);
        startActivity(intent);
    }
}