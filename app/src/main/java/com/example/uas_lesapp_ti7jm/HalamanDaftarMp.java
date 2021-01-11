package com.example.uas_lesapp_ti7jm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class HalamanDaftarMp extends AppCompatActivity {
    EditText nama, nowa, almt;
    Spinner pndk;
    ImageView dftr_btn, btn_add_photo, pic_photo_register_user;;

    DatabaseReference reference;
    String MOPRO_KEY = "moprokey";
    String mopro_key = "";

    String mopro_key_new = "";
    Uri photo_location;
    Integer photo_max = 1;

    StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar_mp);

        getUsernameLocal();
        nama = (EditText)findViewById(R.id.ed_nama);
        nowa = (EditText)findViewById(R.id.ed_nowa);
        almt = (EditText)findViewById(R.id.ed_alamat);
        pndk = (Spinner)findViewById(R.id.spn_pdkn);
        dftr_btn = (ImageView)findViewById(R.id.btn_daftar1);
        btn_add_photo = (ImageView)findViewById(R.id.foto_mp);
        pic_photo_register_user = findViewById(R.id.foto_mp);

        pndk = findViewById(R.id.spn_pdkn);
        String[] pendidikan = {"SMP", "SMK/SMA", "Kuliah"};
        ArrayAdapter adapter = new ArrayAdapter(HalamanDaftarMp.this,
                android.R.layout.simple_spinner_dropdown_item, pendidikan);
        pndk.setAdapter(adapter);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        dftr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference().child("mobile_pro/data_mp");
                storage = FirebaseStorage.getInstance().getReference().child("Foto");
                if(photo_location != null) {
                    StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "."+ getFileExtension(photo_location));
                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //String uri_photo = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            String uri_photo = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            reference.getRef().child("Foto").setValue(uri_photo);
                            reference.getRef().child("nama_mp").setValue(nama.getText().toString());
                            reference.getRef().child("pndk_mp").setValue(pndk.getSelectedItem().toString());
                            reference.getRef().child("no_mp").setValue(nowa.getText().toString());
                            reference.getRef().child("alamat_mp").setValue(almt.getText().toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent gotoregister2register = new Intent(HalamanDaftarMp.this, MainActivity.class);
                            startActivity(gotoregister2register);
                        }
                    });
                }
            }
        });

    }

    String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(MOPRO_KEY, MODE_PRIVATE);
        mopro_key_new = sharedPreferences.getString(mopro_key, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == photo_max && resultCode == RESULT_OK && data!= null && data.getData() != null) {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_photo_register_user);
        }

    }
}