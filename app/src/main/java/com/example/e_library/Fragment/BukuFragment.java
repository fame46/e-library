package com.example.e_library.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_library.Adapter.AdapterBuku;
import com.example.e_library.Adapter.AdapterKategori;
import com.example.e_library.DetailBukuActivity;
import com.example.e_library.Model.ModelBuku;
import com.example.e_library.Model.ModelFavorit;
import com.example.e_library.Model.ModelKategori;
import com.example.e_library.PdfViewActivity;
import com.example.e_library.R;
import com.example.e_library.Utils.DataHelper;
import com.example.e_library.Utils.RecyclerItemClickListener;
import com.example.e_library.Utils.URLs;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BukuFragment extends Fragment implements AdapterBuku.FavoritListener, AdapterBuku.BookmarksListener, AdapterBuku.ViewPdfListener{

    List<ModelBuku> modelBukuList = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    DividerItemDecoration did;
    RecyclerView.Adapter adapter;
    DataHelper dbHelper;

    ProgressBar loading;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    private AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buku, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewBuku);
        loading = v.findViewById(R.id.loading);

        adapter = new AdapterBuku(getContext(),modelBukuList,BukuFragment.this,BukuFragment.this, BukuFragment.this);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(did);
        recyclerView.setAdapter(adapter);

        loadPengadaanData();

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

    private void loadPengadaanDataCari(String judul){
        loading.setVisibility(View.VISIBLE);
        modelBukuList.clear();
        StringRequest jsonObjectRequest = new StringRequest(com.android.volley.Request.Method.GET, URLs.CARIBUKU+"?judul="+judul, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Aaaa",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelBuku modelBuku = new ModelBuku();
                                modelBuku.setId(object.getInt("id"));
                                modelBuku.setCover(object.getString("cover"));
                                modelBuku.setJudul(object.getString("judul"));
                                modelBuku.setContent(object.getString("content"));
                                modelBuku.setPenerbit(object.getString("penerbit"));
                                modelBuku.setTahun(object.getInt("tahun"));
                                modelBuku.setDeskripsi(object.getString("deskripsi"));
                                modelBuku.setPengarang(object.getString("pengarang"));
                                modelBuku.setId_kategori(object.getInt("id_kategori"));
                                modelBukuList.add(modelBuku);
                                loading.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
//                                loading.setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    loading.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
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
                    loadPengadaanDataCari(newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    loadPengadaanDataCari(query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }

    private void loadPengadaanData(){
        loading.setVisibility(View.VISIBLE);
        modelBukuList.clear();
        StringRequest jsonObjectRequest = new StringRequest(com.android.volley.Request.Method.GET, URLs.BUKU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Aaaa",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    int success = jsonObject.getInt("success");
                    if (success == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject object = jsonArray.getJSONObject(i);
                                ModelBuku modelBuku = new ModelBuku();
                                modelBuku.setId(object.getInt("id"));
                                modelBuku.setCover(object.getString("cover"));
                                modelBuku.setJudul(object.getString("judul"));
                                modelBuku.setContent(object.getString("content"));
                                modelBuku.setPenerbit(object.getString("penerbit"));
                                modelBuku.setTahun(object.getInt("tahun"));
                                modelBuku.setDeskripsi(object.getString("deskripsi"));
                                modelBuku.setPengarang(object.getString("pengarang"));
                                modelBuku.setId_kategori(object.getInt("id_kategori"));
                                modelBukuList.add(modelBuku);
                                loading.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                loading.setVisibility(View.GONE);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loading.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
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

    @Override
    public void favoritId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit) {
        dbHelper = new DataHelper(getContext());
        if(dbHelper.getIdCount(id) > 0){
            dbHelper.hapusDataFavorit(id);
            loadPengadaanData();
            Toast.makeText(getContext(), "dihapus dari favorit", Toast.LENGTH_LONG).show();
        }else {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("insert into favorit(id, cover, content, judul, deskripsi, tahun, pengarang, penerbit) values('" +
                    id + "','" +
                    cover + "','" +
                    content + "','" +
                    judul + "','" +
                    deskripsi + "','" +
                    tahun + "','" +
                    pengarang + "','" +
                    penerbit + "')");
            loadPengadaanData();
            Toast.makeText(getContext(), "Ditambahkan ke favorit", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void bookmarksId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit) {
        dbHelper = new DataHelper(getContext());
        if(dbHelper.getIdCountBookmark(id) > 0){
            dbHelper.hapusDataBookmark(id);
            loadPengadaanData();
            Toast.makeText(getContext(), "dihapus dari bookmark", Toast.LENGTH_LONG).show();
        }else {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("insert into bookmarks(id, cover, content, judul, deskripsi, tahun, pengarang, penerbit) values('" +
                    id + "','" +
                    cover + "','" +
                    content + "','" +
                    judul + "','" +
                    deskripsi + "','" +
                    tahun + "','" +
                    pengarang + "','" +
                    penerbit + "')");
            loadPengadaanData();
            Toast.makeText(getContext(), "Ditambahkan ke bookmarks", Toast.LENGTH_LONG).show();
        }
    }
}