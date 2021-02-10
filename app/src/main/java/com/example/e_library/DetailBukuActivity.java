package com.example.e_library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailBukuActivity extends AppCompatActivity {

    ImageView imageView, back;
    TextView tvJudul, tvTahun, tvDeskripsi, tvPengarang,tvPenerbit;
    Button btnbca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);

        back = findViewById(R.id.backChoiceReceive);
        imageView = findViewById(R.id.imageViewSampul);
        tvJudul = findViewById(R.id.tvJudul);
        tvTahun = findViewById(R.id.tvTahun);
        tvDeskripsi = findViewById(R.id.tvDeskripsi);
        tvPengarang = findViewById(R.id.tvPengarang);
        tvPenerbit = findViewById(R.id.tvPenerbit);
        btnbca = findViewById(R.id.btnBaca);

        Glide.with(getApplicationContext()).load(getIntent().getStringExtra("cover")).into(imageView);
        tvJudul.setText(getIntent().getStringExtra("judul"));
        tvTahun.setText(getIntent().getStringExtra("tahun"));
        tvDeskripsi.setText(Html.fromHtml(getIntent().getStringExtra("deskripsi")));
        tvPengarang.setText(getIntent().getStringExtra("pengarang"));
        tvPenerbit.setText(getIntent().getStringExtra("penerbit"));


        btnbca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PdfViewActivity.class);
                intent.putExtra("content", getIntent().getStringExtra("content"));
                intent.putExtra("judul", getIntent().getStringExtra("judul"));
                startActivity(intent);
            }
        });

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
}