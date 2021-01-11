package com.example.uas_lesapp_ti7jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HalamanMobilePro extends AppCompatActivity {
    ImageView lintasdft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_mobile_pro);

        lintasdft = (ImageView)findViewById(R.id.btn_daftar1);
    }

    public void lintasmp (View view) {
        Intent i = new Intent(this, HalamanDaftarMp.class);
        startActivity(i);
    }
}