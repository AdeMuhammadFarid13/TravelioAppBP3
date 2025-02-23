package com.android.travelapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Tickets extends AppCompatActivity {
    // Deklarasi elemen UI untuk menampilkan informasi tiket
    TextView name, email, phone, nameTour, totalPeople, totalPrice;
    Button btnBack;
    AlertDialog dialog;

    SharedPreferences preferences;

    // Kunci untuk menyimpan dan mengambil data dari SharedPreferences
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_NAME_TOUR = "name_tour";
    private static final String KEY_COUNT_ITEMS = "count_items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        // Inisialisasi elemen UI
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        nameTour = findViewById(R.id.name_tour);
        totalPeople = findViewById(R.id.total_people);
        totalPrice = findViewById(R.id.total_price);
        btnBack = findViewById(R.id.btn_back);

        // Mendapatkan SharedPreferences untuk menyimpan dan mengambil data pengguna
        preferences = getSharedPreferences("userInfo", 0);

        // Menambahkan listener untuk tombol "Back" yang akan kembali ke Dashboard
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuka Dashboard Activity
                Intent intent = new Intent(Tickets.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        // Mengambil data dari SharedPreferences
        String nameView = preferences.getString(KEY_NAME, null);
        String emailView = preferences.getString(KEY_EMAIL, null);
        String phoneView = preferences.getString(KEY_PHONE, null);

        String nameTourView = preferences.getString(KEY_NAME_TOUR, null);
        String totalItemsView = preferences.getString(KEY_COUNT_ITEMS, null);
        String totalPriceView = preferences.getString(KEY_TOTAL_PRICE, null);

        // Memeriksa apakah data tersedia
        if (nameView != null || emailView != null || phoneView != null || nameTourView != null || totalItemsView != null || totalPriceView != null) {
            // Menampilkan data pengguna dan informasi tiket ke dalam UI
            name.setText(nameView);
            email.setText(emailView);
            phone.setText(phoneView);
            nameTour.setText(nameTourView);
            totalPeople.setText(totalItemsView + " Orang");
            totalPrice.setText("Rp" + totalPriceView);
        } else {
            // Menampilkan dialog jika data tidak ada
            dialog = new AlertDialog.Builder(Tickets.this)
                    .setTitle("Check Tickets")
                    .setMessage("Data is Empty")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Jika data kosong, kembali ke Dashboard
                            Intent intent = new Intent(Tickets.this, Dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    }).show();
        }
    }
}
