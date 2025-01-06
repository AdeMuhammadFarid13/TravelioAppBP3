package com.android.travelapp; // Sesuaikan dengan package aplikasi Anda

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.android.travelapp.Dashboard;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us); // Menghubungkan dengan file XML

        // Mengatur tombol "Back"
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kembali ke Dashboard
                Intent intent = new Intent(AboutUs.this, Dashboard.class);
                startActivity(intent);
                finish(); // Menutup aktivitas AboutUs
            }
        });
    }
}
