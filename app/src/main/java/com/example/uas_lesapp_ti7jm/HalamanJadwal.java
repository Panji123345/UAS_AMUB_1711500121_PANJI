package com.example.uas_lesapp_ti7jm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;

public class HalamanJadwal extends AppCompatActivity {
    ImageView mp, wp, dg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_jadwal);

        mp = (ImageView)findViewById(R.id.btn_mp);
        wp = (ImageView)findViewById(R.id.btn_wp);
        dg = (ImageView)findViewById(R.id.btn_dg);
    }

    public void hal_mp (View view){
        Intent i = new Intent(this, HalamanMobilePro.class);
        startActivity(i);
    }

    public void hal_wp (View view){
        Intent i = new Intent(this, HalamanWebPro.class);
        startActivity(i);
    }

    public void hal_dg (View view){
        Intent i = new Intent(this, HalamanDesignGrap.class);
        startActivity(i);
    }
}