package com.example.uas_lesapp_ti7jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HalamanWebPro extends AppCompatActivity {
    ImageView lintaswp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_web_pro);

        lintaswp = (ImageView)findViewById(R.id.btn_daftar_wp);
    }

    public void listaswp (View view) {
        Intent i = new Intent(this, HalamanDaftarWp.class);
        startActivity(i);
    }
}