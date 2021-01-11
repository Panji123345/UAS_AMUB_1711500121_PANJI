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

public class HalamanDaftarWp extends AppCompatActivity {
    EditText nama1, nowa1, almt1;
    Spinner pndk1;
    ImageView dftr_btn1, btn_add_photo, pic_photo_register_user;;

    DatabaseReference reference;
    String WEBPRO_KEY = "webprokey";
    String webpro_key = "";

    String webpro_key_new = "";
    Uri photo_location1;
    Integer photo_max1 = 1;

    StorageReference storagewp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar_wp);

        getUsernameLocal();
        nama1 = (EditText)findViewById(R.id.ed_nama1);
        nowa1 = (EditText)findViewById(R.id.ed_nowa1);
        almt1 = (EditText)findViewById(R.id.ed_alamat1);
        pndk1 = (Spinner)findViewById(R.id.spn_pdkn1);
        dftr_btn1 = (ImageView)findViewById(R.id.btn_daftar2);
        btn_add_photo = (ImageView)findViewById(R.id.foto_wp);
        pic_photo_register_user = findViewById(R.id.foto_wp);

        pndk1 = findViewById(R.id.spn_pdkn1);
        String[] pendidikan = {"SMP", "SMK/SMA", "Kuliah"};
        ArrayAdapter adapter = new ArrayAdapter(HalamanDaftarWp.this,
                android.R.layout.simple_spinner_dropdown_item, pendidikan);
        pndk1.setAdapter(adapter);

        btn_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto1();
            }
        });

        dftr_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference().child("web_pro/data_wp");
                storagewp = FirebaseStorage.getInstance().getReference().child("Foto");
                if(photo_location1 != null) {
                    StorageReference storageReference1 = storagewp.child(System.currentTimeMillis() + "."+ getFileExtension1(photo_location1));
                    storageReference1.putFile(photo_location1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //String uri_photo = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            String uri_photo = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            reference.getRef().child("Foto").setValue(uri_photo);
                            reference.getRef().child("nama_wp").setValue(nama1.getText().toString());
                            reference.getRef().child("pndk_wp").setValue(pndk1.getSelectedItem().toString());
                            reference.getRef().child("no_wp").setValue(nowa1.getText().toString());
                            reference.getRef().child("alamat_wp").setValue(almt1.getText().toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent gotoregister2register = new Intent(HalamanDaftarWp.this, MainActivity.class);
                            startActivity(gotoregister2register);
                        }
                    });
                }
            }
        });
    }

    String getFileExtension1(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto1() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max1);
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(WEBPRO_KEY, MODE_PRIVATE);
        webpro_key_new = sharedPreferences.getString(webpro_key, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == photo_max1 && resultCode == RESULT_OK && data!= null && data.getData() != null) {
            photo_location1 = data.getData();
            Picasso.with(this).load(photo_location1).centerCrop().fit().into(pic_photo_register_user);
        }

    }
}