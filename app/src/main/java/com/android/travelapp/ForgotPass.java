package com.android.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class ForgotPass extends AppCompatActivity {
    // Inisialisasi input layout dan tombol
    TextInputLayout inpUser;
    Button btnFind;
    TextView btnSignUp;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        // Menghubungkan komponen UI dengan layout
        inpUser = findViewById(R.id.username_find);
        btnFind = findViewById(R.id.btn_find);
        btnSignUp = findViewById(R.id.btn_signUp);

        // Mengambil SharedPreferences untuk menyimpan dan mengambil data pengguna
        preferences = getSharedPreferences("userInfo", 0);

        // Listener untuk tombol "Find" yang digunakan untuk mencari akun pengguna
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil username yang dimasukkan oleh pengguna
                String userValue = inpUser.getEditText().getText().toString();
                String emailValue = inpUser.getEditText().getText().toString();

                // Mengambil username dan email yang disimpan di SharedPreferences
                String loginUser = preferences.getString("user", "");
                String emailUser = preferences.getString("email", "");

                // Membandingkan apakah username atau email yang dimasukkan cocok dengan yang ada di SharedPreferences
                if (userValue.equals(loginUser) || emailValue.equals(emailUser)){
                    // Jika cocok, tampilkan pesan sukses dan arahkan pengguna ke halaman EditUser
                    Toast.makeText(ForgotPass.this, "Successfully Find Your Account", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(ForgotPass.this, EditUser.class);
                    startActivity(intent);
                } else {
                    // Jika tidak cocok, tampilkan pesan kesalahan
                    Toast.makeText(ForgotPass.this, "Username or Email not Found", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Listener untuk tombol "SignUp" yang mengarahkan pengguna ke halaman pendaftaran
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgotPass.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}
