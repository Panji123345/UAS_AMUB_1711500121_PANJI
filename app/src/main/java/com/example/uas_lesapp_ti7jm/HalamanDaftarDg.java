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

public class HalamanDaftarDg extends AppCompatActivity {
    EditText namadg, nowadg, almtdg;
    Spinner pndkdg;
    ImageView dftr_btndg, btn_add_photodg, pic_photo_register_userdg;

    DatabaseReference reference;
    String DG_KEY = "dgkey";
    String dg_key = "";

    String dg_key_new = "";
    Uri photo_locationdg;
    Integer photo_maxdg = 1;

    StorageReference storagedg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar_dg);

        getUsernameLocaldg();
        namadg = (EditText)findViewById(R.id.ed_namadg);
        nowadg = (EditText)findViewById(R.id.ed_nowadg);
        almtdg = (EditText)findViewById(R.id.ed_alamatdg);
        pndkdg = (Spinner)findViewById(R.id.spn_pdkndg);
        dftr_btndg = (ImageView)findViewById(R.id.btn_daftardg);
        btn_add_photodg = (ImageView)findViewById(R.id.foto_dg);
        pic_photo_register_userdg = findViewById(R.id.foto_dg);

        pndkdg = findViewById(R.id.spn_pdkndg);
        String[] pendidikan = {"SMP", "SMK/SMA", "Kuliah"};
        ArrayAdapter adapter = new ArrayAdapter(HalamanDaftarDg.this,
                android.R.layout.simple_spinner_dropdown_item, pendidikan);
        pndkdg.setAdapter(adapter);

        btn_add_photodg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhotodg();
            }
        });

        dftr_btndg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference().child("design_grap/data_dg");
                storagedg = FirebaseStorage.getInstance().getReference().child("Foto");
                if(photo_locationdg != null) {
                    StorageReference storageReference1 = storagedg.child(System.currentTimeMillis() + "."+ getFileExtensiondg(photo_locationdg));
                    storageReference1.putFile(photo_locationdg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //String uri_photo = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            String uri_photo = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                            reference.getRef().child("Foto").setValue(uri_photo);
                            reference.getRef().child("nama_dg").setValue(namadg.getText().toString());
                            reference.getRef().child("pndk_dg").setValue(pndkdg.getSelectedItem().toString());
                            reference.getRef().child("no_dg").setValue(nowadg.getText().toString());
                            reference.getRef().child("alamat_dg").setValue(almtdg.getText().toString());
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            Intent gotoregister2register = new Intent(HalamanDaftarDg.this, MainActivity.class);
                            startActivity(gotoregister2register);
                        }
                    });
                }
            }
        });
    }

    String getFileExtensiondg(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhotodg() {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_maxdg);
    }

    public void getUsernameLocaldg() {
        SharedPreferences sharedPreferences = getSharedPreferences(DG_KEY, MODE_PRIVATE);
        dg_key_new = sharedPreferences.getString(dg_key, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == photo_maxdg && resultCode == RESULT_OK && data!= null && data.getData() != null) {
            photo_locationdg = data.getData();
            Picasso.with(this).load(photo_locationdg).centerCrop().fit().into(pic_photo_register_userdg);
        }

    }
}