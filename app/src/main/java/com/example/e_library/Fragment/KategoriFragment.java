package com.example.e_library.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.e_library.Adapter.AdapterKategori;
import com.example.e_library.Model.ModelKategori;
import com.example.e_library.R;
import com.example.e_library.Utils.URLs;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KategoriFragment extends Fragment {

    List<ModelKategori> modelKategoriList = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager llm;
    DividerItemDecoration did;
    RecyclerView.Adapter adapter;

    private AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_kategori, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewKategori);

        adapter = new AdapterKategori(modelKategoriList);
        llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        did = new DividerItemDecoration(recyclerView.getContext(), llm.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(did);
        recyclerView.setAdapter(adapter);

        loadPengadaanData();

        MobileAds.initialize(getContext(), "ca-app-pub-3940256099942544~3347511713");
        adView = (AdView)v.findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        return v;
    }

    private void loadPengadaanData(){
        modelKategoriList.clear();
        StringRequest jsonObjectRequest = new StringRequest(com.android.volley.Request.Method.GET, URLs.KATEGORI, new Response.Listener<String>() {
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
                                ModelKategori modelKategori = new ModelKategori();
                                modelKategori.setId(object.getInt("id"));
                                modelKategori.setNama_kategori(object.getString("nama_kategori"));
                                modelKategori.setDeskripsi(object.getString("deskripsi"));
                                modelKategoriList.add(modelKategori);
//                                loading.setVisibility(View.GONE);
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
}