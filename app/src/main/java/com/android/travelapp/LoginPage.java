package com.android.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

public class LoginPage extends AppCompatActivity {
    // Deklarasi elemen-elemen UI
    TextView btnSignUp;
    ImageView img;
    TextView txtTitle, txtSub;
    TextInputLayout txtUser, txtPass;
    LinearLayout txtSignUp;
    Button btnLogin, btnForgotPass;

    // SharedPreferences untuk mengambil data pengguna yang sudah terdaftar
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengatur tampilan fullscreen untuk activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW);
        setContentView(R.layout.activity_login_page); // Menghubungkan layout XML dengan activity ini

        // Menghubungkan elemen UI dengan ID-nya
        img = findViewById(R.id.img_logo);
        txtTitle = findViewById(R.id.tv_titleLogin);
        txtSub = findViewById(R.id.subtitleLogin);
        txtUser = findViewById(R.id.username_login);
        txtPass = findViewById(R.id.password_login);
        btnLogin = findViewById(R.id.btn_login);
        btnForgotPass = findViewById(R.id.btn_forgotPass);
        btnSignUp = findViewById(R.id.btn_signUp);
        txtSignUp = findViewById(R.id.ll_signup);

        // Menginisialisasi SharedPreferences untuk mengambil data pengguna yang sudah terdaftar
        preferences = getSharedPreferences("userInfo", 0);

        // Menambahkan listener untuk tombol SignUp
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignUp.class); // Mengarahkan pengguna ke halaman SignUp
                startActivity(intent);
            }
        });

        // Menambahkan listener untuk tombol "Enter" pada keyboard ketika mengetik di password field
        txtPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String userValue = txtUser.getEditText().getText().toString(); // Mengambil nilai username
                    String emailValue = txtUser.getEditText().getText().toString(); // Mengambil nilai email
                    String passValue = txtPass.getEditText().getText().toString(); // Mengambil nilai password

                    // Mengambil data username, email, dan password yang sudah tersimpan di SharedPreferences
                    String loginUser = preferences.getString("user", "");
                    String emailUser = preferences.getString("email", "");
                    String loginPass = preferences.getString("pass", "");

                    // Memeriksa apakah username atau email dan password cocok dengan yang ada di SharedPreferences
                    if (userValue.equals(loginUser) && passValue.equals(loginPass) || emailValue.equals(emailUser) && passValue.equals(loginPass)) {
                        // Jika cocok, masuk ke Dashboard activity
                        Intent intent = new Intent(LoginPage.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginPage.this, "Login", Toast.LENGTH_LONG).show();
                    } else {
                        // Menampilkan pesan jika username atau password tidak cocok
                        Toast.makeText(LoginPage.this, "Username or Password doesn't match", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }
        });

        // Menambahkan listener untuk tombol Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userValue = txtUser.getEditText().getText().toString(); // Mengambil nilai username
                String emailValue = txtUser.getEditText().getText().toString(); // Mengambil nilai email
                String passValue = txtPass.getEditText().getText().toString(); // Mengambil nilai password

                // Mengambil data username, email, dan password yang sudah tersimpan di SharedPreferences
                String loginUser = preferences.getString("user", "");
                String emailUser = preferences.getString("email", "");
                String loginPass = preferences.getString("pass", "");

                // Memeriksa apakah username atau email dan password cocok dengan yang ada di SharedPreferences
                if (userValue.equals(loginUser) && passValue.equals(loginPass) || emailValue.equals(emailUser) && passValue.equals(loginPass)) {
                    // Jika cocok, masuk ke Dashboard activity
                    Intent intent = new Intent(LoginPage.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginPage.this, "Login", Toast.LENGTH_LONG).show();
                } else {
                    // Menampilkan pesan jika username atau password tidak cocok
                    Toast.makeText(LoginPage.this, "Username or Password doesn't match", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Menambahkan listener untuk tombol Forgot Password
        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // Menutup activity login dan mengarahkan ke halaman ForgotPass
                Intent intent = new Intent(LoginPage.this, ForgotPass.class);
                startActivity(intent);
            }
        });
    }
}
