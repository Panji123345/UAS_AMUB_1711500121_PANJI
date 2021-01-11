package com.example.uas_lesapp_ti7jm;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;

public class HalamanDaftar extends AppCompatActivity {
    ImageView dfmp, dfwp, dfdg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar);

        dfmp = (ImageView) findViewById(R.id.btn_dfmp);
        dfwp = (ImageView) findViewById(R.id.btn_dfwp);
        dfdg = (ImageView) findViewById(R.id.btn_dfdg);
    }

    public void haldafmp(View view) {
        Intent i = new Intent(this, HalamanDaftarMp.class);
        startActivity(i);
    }

    public void haldafwp(View view) {
        Intent i = new Intent(this, HalamanDaftarWp.class);
        startActivity(i);
    }

    public void haldafdg(View view) {
        Intent i = new Intent(this, HalamanDaftarDg.class);
        startActivity(i);
    }
}