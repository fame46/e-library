package com.example.e_library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_library.Adapter.AdapterBuku;
import com.example.e_library.Fragment.BukuFragment;
import com.example.e_library.Model.ModelBuku;
import com.example.e_library.Utils.DataHelper;
import com.example.e_library.Utils.URLs;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BukuKategoriActivity extends AppCompatActivity implements AdapterBuku.FavoritListener, AdapterBuku.BookmarksListener, AdapterBuku.ViewPdfListener{

    private ProgressBar loading;
    ImageView back;

    List<ModelBuku> modelBukuList = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    DividerItemDecoration did;
    RecyclerView.Adapter adapter;
    DataHelper dbHelper;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku_kategori);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = findViewById(R.id.recyclerViewBuku);
        loading = findViewById(R.id.loading);
        back = findViewById(R.id.backChoiceReceive);

        adapter = new AdapterBuku(getApplicationContext(),modelBukuList, this,this, this);
        llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(did);
        recyclerView.setAdapter(adapter);

        loadPengadaanData();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544~3347511713");
        adView = (AdView)findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void loadPengadaanData(){
        loading.setVisibility(View.VISIBLE);
        modelBukuList.clear();
        StringRequest jsonObjectRequest = new StringRequest(com.android.volley.Request.Method.GET, URLs.BUKUKATEGORI+"?id_kategori="+getIntent().getStringExtra("id_kategori"), new Response.Listener<String>() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void pdfId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit) {
        Intent intent = new Intent(getApplicationContext(), DetailBukuActivity.class);
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
        dbHelper = new DataHelper(getApplicationContext());
        if(dbHelper.getIdCount(id) > 0){
            dbHelper.hapusDataFavorit(id);
            loadPengadaanData();
            Toast.makeText(getApplicationContext(), "dihapus dari favorit", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), "Ditambahkan ke favorit", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void bookmarksId(String id, String cover, String content, String judul, String deskripsi, String tahun, String pengarang, String penerbit) {
        dbHelper = new DataHelper(getApplicationContext());
        if(dbHelper.getIdCountBookmark(id) > 0){
            dbHelper.hapusDataBookmark(id);
            loadPengadaanData();
            Toast.makeText(getApplicationContext(), "dihapus dari bookmark", Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), "Ditambahkan ke bookmarks", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchIem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchIem.getActionView();
        searchView.setQueryHint("Masukkan Nama Barang . . .");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Untuk memfilter data dari ArrayAdapter
                loadPengadaanDataCari(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //Data akan berubah saat user menginputkan text/kata kunci pada SearchView
                loadPengadaanDataCari(s);
                return false;
            }
        });

        return true;
    }

    private void loadPengadaanDataCari(String judul){
        loading.setVisibility(View.VISIBLE);
        modelBukuList.clear();
        StringRequest jsonObjectRequest = new StringRequest(com.android.volley.Request.Method.GET, URLs.CARIBUKUKATEGORI+"?judul="+judul+"&id_kategori="+getIntent().getStringExtra("id_kategori"), new Response.Listener<String>() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

}