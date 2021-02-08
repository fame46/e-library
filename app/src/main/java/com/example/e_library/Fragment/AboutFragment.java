package com.example.e_library.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e_library.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AboutFragment extends Fragment {

    TextView tvLink;
    private AdView adView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);

//        tvLink = v.findViewById(R.id.link);

//        tvLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://library.pktj.ac.id/"));
//                startActivity(browserIntent);
//            }
//        });

        MobileAds.initialize(getContext(), "ca-app-pub-2707736392233075/5248913902");
        adView = (AdView)v.findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);

        return v;
    }
}