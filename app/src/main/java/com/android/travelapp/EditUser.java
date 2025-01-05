package com.android.travelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditUser extends AppCompatActivity {
    // Deklarasi input layout dan tombol
    TextInputLayout inpName, inpEmail, inpPhone, inpUser, inpPass, inpRePass;
    Button btnUpdate, btnReset;

    SharedPreferences preferences;

    // Kunci SharedPreferences untuk menyimpan data pengguna
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";
    private static final String KEY_REPASS = "repass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Inisialisasi elemen UI
        inpName = findViewById(R.id.name_edit);
        inpEmail = findViewById(R.id.email_edit);
        inpPhone = findViewById(R.id.phone_edit);
        inpUser = findViewById(R.id.username_edit);
        inpPass = findViewById(R.id.password_edit);
        inpRePass = findViewById(R.id.retype_password_edit);

        btnUpdate = findViewById(R.id.btn_update);
        btnReset = findViewById(R.id.btn_reset);

        // Mengambil data pengguna yang tersimpan di SharedPreferences
        preferences = getSharedPreferences("userInfo", 0);

        // Mengisi field dengan data yang sudah ada
        String nameView = preferences.getString(KEY_NAME, null);
        String emailView = preferences.getString(KEY_EMAIL, null);
        String phoneView = preferences.getString(KEY_PHONE, null);
        String userView = preferences.getString(KEY_USER, null);
        String passView = preferences.getString(KEY_PASS, null);
        String repassView = preferences.getString(KEY_REPASS, null);

        // Jika data ada, set ke input field
        if (nameView != null || emailView != null || phoneView != null || userView != null || passView != null || repassView != null){
            inpName.getEditText().setText(nameView);
            inpEmail.getEditText().setText(emailView);
            inpPhone.getEditText().setText(phoneView);
            inpUser.getEditText().setText(userView);
            inpPass.getEditText().setText(passView);
            inpRePass.getEditText().setText(repassView);
        }

        // Listener untuk tombol Update (perbarui data pengguna)
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil nilai input
                String nameValue = inpName.getEditText().getText().toString();
                String emailValue = inpEmail.getEditText().getText().toString();
                String phoneValue = inpPhone.getEditText().getText().toString();
                String userValue = inpUser.getEditText().getText().toString();
                String passValue = inpPass.getEditText().getText().toString();
                String repassValue = inpRePass.getEditText().getText().toString();

                // Mengecek apakah password dan re-password cocok
                if (passValue.equals(repassValue)){
                    // Menyimpan data yang diperbarui ke SharedPreferences
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(KEY_NAME, nameValue);
                    editor.putString(KEY_EMAIL, emailValue);
                    editor.putString(KEY_PHONE, phoneValue);
                    editor.putString(KEY_USER, userValue);
                    editor.putString(KEY_PASS, passValue);
                    editor.putString(KEY_REPASS, repassValue);
                    editor.putString("Authentication_Status","true");  // Menandai status autentikasi
                    editor.apply();

                    try {
                        // Jika ada input yang kosong, beri peringatan
                        if (nameValue.equals("") || emailValue.equals("") || phoneValue.equals("") || userValue.equals("") || passValue.equals("") || repassValue.equals("")){
                            Toast.makeText(EditUser.this, "Data Cannot be Empty. \nData can be Exhausted.", Toast.LENGTH_LONG).show();
                        } else {
                            // Jika data valid, tampilkan pesan sukses dan kembali ke halaman login
                            String name = preferences.getString(KEY_NAME, null);
                            if (name != null){
                                Toast.makeText(EditUser.this, "Successful Registration", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(EditUser.this, LoginPage.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    } catch (Exception e){
                        // Menangani kesalahan jika username sudah digunakan
                        Toast.makeText(EditUser.this, "Username has been used", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Jika password dan re-password tidak cocok
                    Toast.makeText(EditUser.this, "Password doesn't match", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Listener untuk tombol Reset (reset semua input)
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    // Fungsi untuk mereset semua input
    public void reset(){
        inpName.getEditText().setText(null);
        inpEmail.getEditText().setText(null);
        inpPhone.getEditText().setText(null);
        inpUser.getEditText().setText(null);
        inpPass.getEditText().setText(null);
        inpRePass.getEditText().setText(null);
    }
}
