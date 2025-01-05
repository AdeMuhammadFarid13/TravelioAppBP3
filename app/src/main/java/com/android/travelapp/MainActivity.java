package com.android.travelapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.SplashScreen;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000; // Durasi splash screen dalam milidetik

    // Deklarasi untuk animasi dan elemen-elemen UI
    Animation topAnim, bottomAnim;
    ImageView img;
    TextView txtNameApp, txtSubNameApp, txtCopyright;

    @RequiresApi(api = Build.VERSION_CODES.P) // Memastikan aplikasi hanya berjalan di perangkat dengan Android P atau lebih baru
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengatur tampilan aplikasi menjadi fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW);
        setContentView(R.layout.activity_main); // Menghubungkan layout dengan activity

        // Memuat animasi dari resource animasi
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // Menghubungkan elemen UI dengan ID yang sesuai
        img = findViewById(R.id.img_logo);
        txtNameApp = findViewById(R.id.tv_nameApp);
        txtSubNameApp = findViewById(R.id.tv_sub_nameApp);
        txtCopyright = findViewById(R.id.tv_copyright);

        // Menambahkan animasi pada elemen-elemen UI
        img.setAnimation(topAnim);
        txtNameApp.setAnimation(bottomAnim);
        txtSubNameApp.setAnimation(bottomAnim);
        txtCopyright.setAnimation(bottomAnim);

        // Menggunakan Handler untuk menunda eksekusi selama SPLASH_SCREEN (3000ms atau 3 detik)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Setelah durasi selesai, mengarahkan pengguna ke halaman login
                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                startActivity(intent);
                finish(); // Menutup activity utama agar tidak bisa kembali ke halaman splash screen
            }
        }, SPLASH_SCREEN); // Menunggu selama 3000ms (3 detik)
    }
}
