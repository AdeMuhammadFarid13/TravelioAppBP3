package com.android.travelapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;

public class SignUp extends AppCompatActivity {
    // Deklarasi elemen-elemen UI
    TextView txtSignIn;
    ImageView img;
    TextView txtTitle, txtSub;
    TextInputLayout inpFullname, inpEmail, inpPhone, inpUser, inpPass, inpRePass;
    Button btnSignUp, btnReset;

    // SharedPreferences untuk menyimpan data pengguna
    SharedPreferences preferences;

    // Kunci untuk menyimpan dan mengambil data dari SharedPreferences
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_REPASS = "repass";

    // Kunci untuk menyimpan detail tur (opsional)
    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_NAME_TOUR = "name_tour";
    private static final String KEY_COUNT_ITEMS = "count_items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); // Menyambungkan layout XML ke activity ini

        // Menghubungkan elemen UI dengan ID-nya
        img = findViewById(R.id.img_logo);
        txtTitle = findViewById(R.id.tv_title_regis);
        inpFullname = findViewById(R.id.name);
        inpEmail= findViewById(R.id.email);
        inpPhone= findViewById(R.id.phone);
        inpUser = findViewById(R.id.username_regis);
        inpPass = findViewById(R.id.password_regis);
        inpRePass = findViewById(R.id.retype_password);
        btnSignUp = findViewById(R.id.btn_signUp);
        txtSignIn = findViewById(R.id.btn_signIn);
        btnReset = findViewById(R.id.btn_reset);

        // Menginisialisasi SharedPreferences untuk menyimpan data pengguna
        preferences = getSharedPreferences("userInfo", 0);

        // Menambahkan listener untuk menangani event ketika tombol "Enter" pada keyboard ditekan
        inpRePass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                // Mengambil nilai yang dimasukkan pada form
                String nameValue = inpFullname.getEditText().getText().toString();
                String emailValue = inpEmail.getEditText().getText().toString();
                String phoneValue = inpPhone.getEditText().getText().toString();
                String userValue = inpUser.getEditText().getText().toString();
                String passValue = inpPass.getEditText().getText().toString();
                String repassValue = inpRePass.getEditText().getText().toString();

                // Memeriksa kecocokan antara password dan re-password
                if (passValue.equals(repassValue)){
                    SharedPreferences.Editor editor = preferences.edit();
                    // Menyimpan data pengguna ke SharedPreferences
                    editor.putString(KEY_NAME, nameValue);
                    editor.putString(KEY_EMAIL, emailValue);
                    editor.putString(KEY_PHONE, phoneValue);
                    editor.putString(KEY_USER, userValue);
                    editor.putString(KEY_PASS, passValue);
                    editor.putString(KEY_REPASS, repassValue);
                    editor.apply();

                    // Memeriksa jika ada data yang kosong dan menampilkan pesan jika ada field kosong
                    try{
                        if (nameValue.equals("") || emailValue.equals("") || phoneValue.equals("") || userValue.equals("") || passValue.equals("") || repassValue.equals("")){
                            Toast.makeText(SignUp.this, "Data Cannot be Empty. \nData can be Exhausted.", Toast.LENGTH_LONG).show();
                        }else{
                            // Jika data valid, lanjutkan ke LoginPage
                            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                                Toast.makeText(SignUp.this, "Registrasi Berhasil!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUp.this, LoginPage.class);
                                startActivity(intent);
                                resetDetailTour(); // Reset data tur yang ada (opsional)
                                finish(); // Menutup activity SignUp
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(SignUp.this, "Username sudah terpakai", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Menampilkan pesan error jika password dan re-password tidak cocok
                    Toast.makeText(SignUp.this, "Password tidak cocok", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });

        // Setiap kali tombol SignUp diklik
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil nilai dari form input
                String nameValue = inpFullname.getEditText().getText().toString();
                String emailValue = inpEmail.getEditText().getText().toString();
                String phoneValue = inpPhone.getEditText().getText().toString();
                String userValue = inpUser.getEditText().getText().toString();
                String passValue = inpPass.getEditText().getText().toString();
                String repassValue = inpRePass.getEditText().getText().toString();

                // Memeriksa apakah password dan re-password cocok
                if (passValue.equals(repassValue)) {
                    SharedPreferences.Editor editor = preferences.edit();
                    // Menyimpan data pengguna ke SharedPreferences
                    editor.putString(KEY_NAME, nameValue);
                    editor.putString(KEY_EMAIL, emailValue);
                    editor.putString(KEY_PHONE, phoneValue);
                    editor.putString(KEY_USER, userValue);
                    editor.putString(KEY_PASS, passValue);
                    editor.putString(KEY_REPASS, repassValue);
                    editor.apply();

                    try {
                        // Memeriksa jika ada field yang kosong
                        if (nameValue.equals("") || emailValue.equals("") || phoneValue.equals("") || userValue.equals("") || passValue.equals("") || repassValue.equals("")) {
                            Toast.makeText(SignUp.this, "Data Cannot be Empty. \nData can be Exhausted.", Toast.LENGTH_LONG).show();
                        } else {
                            // Menampilkan notifikasi dan mengarahkan ke LoginPage
                            Toast.makeText(SignUp.this, "Successful Registration", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignUp.this, LoginPage.class);
                            startActivity(intent);
                            resetDetailTour(); // Reset data tur yang ada
                            finish(); // Menutup activity SignUp
                        }
                    } catch (Exception e) {
                        Toast.makeText(SignUp.this, "Username has been used", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Menampilkan pesan error jika password dan re-password tidak cocok
                    Toast.makeText(SignUp.this, "Password doesn't match", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Setiap kali tombol Reset diklik, reset semua inputan
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(); // Memanggil fungsi reset()
            }
        });

        // Setiap kali tombol Sign In diklik, mengarahkan pengguna ke LoginPage
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }

    // Fungsi untuk mereset semua field input
    public void reset(){
        inpFullname.getEditText().setText(null);
        inpEmail.getEditText().setText(null);
        inpPhone.getEditText().setText(null);
        inpUser.getEditText().setText(null);
        inpPass.getEditText().setText(null);
        inpRePass.getEditText().setText(null);
    }

    // Fungsi untuk menghapus detail tur dari SharedPreferences
    private void resetDetailTour(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NAME_TOUR, null);
        editor.putString(KEY_COUNT_ITEMS, null);
        editor.putString(KEY_TOTAL_PRICE, null);
        editor.apply();
    }
}
