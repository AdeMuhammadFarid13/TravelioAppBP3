package com.android.travelapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TourDetail extends AppCompatActivity {
    // Deklarasi variabel UI
    ImageView imgTour;
    TextView nameTour, descTour, priceTour, txtCount;
    Button addCount, subCount, btnPay;
    ImageButton btnLoc;
    int mCount = 1;  // Variabel untuk menghitung jumlah tiket yang dipilih

    SharedPreferences preferences;

    // Kunci untuk SharedPreferences
    private static final String KEY_IMG_TOUR = "img_tour";
    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_NAME_TOUR = "name_tour";
    private static final String KEY_LOC = "loc_tour";
    private static final String KEY_COUNT_ITEMS = "count_items";
    private static final String KEY_PRICE_TOUR = "price_tour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        preferences = getSharedPreferences("userInfo", 0);

        // Inisialisasi elemen UI
        imgTour = findViewById(R.id.img_tour);
        nameTour = findViewById(R.id.name_tour);
        descTour = findViewById(R.id.desc_tour);
        priceTour = findViewById(R.id.price_tour);
        txtCount = findViewById(R.id.txt_count);
        addCount = findViewById(R.id.btn_addCount);
        subCount = findViewById(R.id.btn_subCount);
        btnPay = findViewById(R.id.btn_pay);
        btnLoc = findViewById(R.id.btn_img_loc);

        // Menampilkan jumlah awal (mCount = 1)
        txtCount.setText(Integer.toString(mCount));

        // Memanggil fungsi untuk mendapatkan data dari Intent
        getDataAdapter();

        // Listener untuk tombol "Add Count" (Menambah jumlah tiket)
        addCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCount++;
                txtCount.setText(Integer.toString(mCount));

                if (getIntent().hasExtra("priceTour")) {
                    int price_tour = getIntent().getIntExtra("priceTour", 0);
                    int totalPrice = price_tour * mCount;
                    priceTour.setText(Integer.toString(totalPrice));  // Mengupdate harga total
                }
            }
        });

        // Listener untuk tombol "Subtract Count" (Mengurangi jumlah tiket)
        subCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCount > 1) {
                    mCount--;
                    txtCount.setText(Integer.toString(mCount));

                    int price_tour = getIntent().getIntExtra("priceTour", 0);
                    int totalPrice = price_tour * mCount;
                    priceTour.setText(Integer.toString(totalPrice));  // Mengupdate harga total
                }
            }
        });

        // Listener untuk tombol "Pay" (Melanjutkan ke halaman pembayaran)
        btnPay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                int price_tour = getIntent().getIntExtra("priceTour", 0);
                int priceValue = price_tour;
                String imageValue = getIntent().getStringExtra("imgTour");
                String nameTourValue = nameTour.getText().toString();
                String totalItemsValue = txtCount.getText().toString();
                String totalPriceValue = priceTour.getText().toString();

                // Menyimpan informasi tiket yang dipilih ke SharedPreferences
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(KEY_IMG_TOUR, imageValue);
                editor.putString(KEY_NAME_TOUR, nameTourValue);
                editor.putString(KEY_COUNT_ITEMS, totalItemsValue);
                editor.putString(KEY_PRICE_TOUR, String.valueOf(priceValue));
                editor.putString(KEY_TOTAL_PRICE, totalPriceValue);
                editor.apply();

                // Membuka activity Receipt untuk menampilkan rincian tiket dan pembayaran
                Intent intent = new Intent(TourDetail.this, Receipt.class);
                startActivity(intent);
                finish();
            }
        });

        // Listener untuk tombol lokasi (peta)
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getIntent().hasExtra("locTour")) {
                    String txtLoc = getIntent().getStringExtra("locTour");
                    Uri uri = Uri.parse("geo:0,0?q=" + txtLoc);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    // Mengecek apakah aplikasi peta tersedia dan membuka peta dengan lokasi
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            }
        });
    }

    // Fungsi untuk mengambil data yang diteruskan dari activity sebelumnya (intent)
    private void getDataAdapter() {
        if (getIntent().hasExtra("imgTour") && getIntent().hasExtra("nameTour") && getIntent().hasExtra("descTour") && getIntent().hasExtra("priceTour")) {
            String image_tour = getIntent().getStringExtra("imgTour");
            String name_tour = getIntent().getStringExtra("nameTour");
            String desc_tour = getIntent().getStringExtra("descTour");
            int price_tour = getIntent().getIntExtra("priceTour", 0);

            // Menyusun data yang diterima ke dalam UI
            setDataDetail(image_tour, name_tour, desc_tour, price_tour);
        }
    }

    // Fungsi untuk mengisi data detail di UI
    private void setDataDetail(String image_tour, String name_tour, String desc_tour, int price_tour) {
        // Menggunakan Glide untuk menampilkan gambar dari URL
        Glide.with(this).asBitmap().load(image_tour).into(imgTour);

        nameTour.setText(name_tour);
        descTour.setText(desc_tour);
        priceTour.setText(Integer.toString(price_tour));
    }
}
