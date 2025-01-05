package com.android.travelapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class Receipt extends AppCompatActivity {
    // Deklarasi komponen UI yang digunakan dalam activity
    ImageView imgTour;
    TextView nameTour, totalPeople, priceTour, totalPrice, name, email, phone;
    Button btnConfirm;
    AlertDialog dialog;
    SharedPreferences preferences;

    // ID untuk channel notifikasi
    String CHANNEL_ID = "Travel App v1";

    // Kunci untuk SharedPreferences
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_IMG_TOUR = "img_tour";
    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_NAME_TOUR = "name_tour";
    private static final String KEY_COUNT_ITEMS = "count_items";
    private static final String KEY_PRICE_TOUR = "price_tour";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // Inisialisasi komponen UI dengan ID yang sesuai
        imgTour = findViewById(R.id.img_tour);
        nameTour = findViewById(R.id.name_tour);
        totalPeople = findViewById(R.id.total_people);
        priceTour = findViewById(R.id.price_tour);
        totalPrice = findViewById(R.id.total_price);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        btnConfirm = findViewById(R.id.btn_confirm);

        // Mengambil data yang tersimpan di SharedPreferences
        preferences = getSharedPreferences("userInfo", 0);
        String nameView = preferences.getString(KEY_NAME, null);
        String emailView = preferences.getString(KEY_EMAIL, null);
        String phoneView = preferences.getString(KEY_PHONE, null);
        String imgTourView = preferences.getString(KEY_IMG_TOUR, null);
        String nameTourView = preferences.getString(KEY_NAME_TOUR, null);
        String totalItemsView = preferences.getString(KEY_COUNT_ITEMS, null);
        String priceView = preferences.getString(KEY_PRICE_TOUR, null);
        String totalPriceView = preferences.getString(KEY_TOTAL_PRICE, null);

        // Menampilkan data yang diambil dari SharedPreferences ke UI
        if (nameView != null || emailView != null || phoneView != null || nameTourView != null || totalItemsView != null || priceView != null || totalPriceView != null || imgTourView != null){
            Glide.with(this).asBitmap().load(imgTourView).into(imgTour);
            name.setText(nameView);
            email.setText(emailView);
            phone.setText(phoneView);
            nameTour.setText(nameTourView);
            priceTour.setText("Rp"+priceView);
            totalPeople.setText(totalItemsView + " Orang");
            totalPrice.setText("Rp"+totalPriceView);
        }

        // Menambahkan listener pada tombol konfirmasi
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat dialog konfirmasi untuk booking
                dialog = new AlertDialog.Builder(Receipt.this)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setTitle("Message")
                        .setMessage("\nAre you sure booked this spot?")  // Pesan konfirmasi
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Jika pengguna memilih "Yes", tampilkan toast dan kirim notifikasi
                                Toast.makeText(Receipt.this, "Success Booked Ticket", Toast.LENGTH_LONG).show();

                                // Membuat Intent untuk membuka activity Receipt setelah konfirmasi
                                Intent intent = new Intent(Receipt.this, Receipt.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                PendingIntent pendingIntent = PendingIntent.getActivity(Receipt.this, 0, intent, 0);

                                // Membuat notifikasi
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(Receipt.this, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_ticket)  // Ikon notifikasi
                                        .setContentTitle("Detail Ticket")  // Judul notifikasi
                                        .setStyle(new NotificationCompat.BigTextStyle()
                                                .bigText("\nYour Ticket Successfully Booked!\n" +
                                                        "=====================================" + "\n" +
                                                        "Nama Pemesan\t: "+nameView+ "\n" +
                                                        "Nama Tempat\t: "+nameTourView+ "\n" +
                                                        "Total Orang\t: "+totalItemsView+ "\n" +
                                                        "Total Harga\t: Rp"+totalPriceView+ "\n" +
                                                        "====================================="))  // Menampilkan detail booking
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setContentIntent(pendingIntent)  // Intent yang akan dibuka ketika notifikasi diklik
                                        .setAutoCancel(true);  // Menutup notifikasi saat diklik

                                // Menampilkan notifikasi
                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Receipt.this);
                                notificationManager.notify(25, builder.build());

                                // Menutup activity setelah booking sukses
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Jika pengguna memilih "No", reset detail tour dan kembali ke activity Dashboard
                                resetDetailTour();
                                Toast.makeText(Receipt.this, "Fail Booked Ticket", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Receipt.this, Dashboard.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();  // Menampilkan dialog
            }
        });
    }

    // Fungsi untuk membuat notification channel (diperlukan pada API level 26+)
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);  // Nama channel
            String description = getString(R.string.channel_desc);  // Deskripsi channel
            int importance = NotificationManager.IMPORTANCE_DEFAULT;  // Prioritas notifikasi
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Mendaftar channel dengan sistem
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Fungsi untuk mereset detail tour di SharedPreferences
    private void resetDetailTour(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NAME_TOUR, null);
        editor.putString(KEY_COUNT_ITEMS, null);
        editor.putString(KEY_TOTAL_PRICE, null);
        editor.apply();  // Menyimpan perubahan
    }
}
