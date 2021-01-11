package com.example.uas_lesapp_ti7jm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView jadwal, daftarles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jadwal = (ImageView)findViewById(R.id.btn_jadwal);
        daftarles = (ImageView)findViewById(R.id.btn_daftarles);
    }

    public void jadwalles (View view){
        Intent i = new Intent(this, HalamanJadwal.class);
        startActivity(i);
    }

    public void daftar_les (View view){
        Intent i = new Intent(this, HalamanDaftar.class);
        startActivity(i);
    }
}