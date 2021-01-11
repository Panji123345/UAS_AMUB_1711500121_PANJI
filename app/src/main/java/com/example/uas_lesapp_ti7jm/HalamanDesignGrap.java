package com.example.uas_lesapp_ti7jm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HalamanDesignGrap extends AppCompatActivity {
    ImageView lintasdg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_design_grap);

        lintasdg = (ImageView)findViewById(R.id.btn_daftar_dg);
    }

    public void lintasdg (View view) {
        Intent i = new Intent(this, HalamanDaftarDg.class);
        startActivity(i);
    }
}