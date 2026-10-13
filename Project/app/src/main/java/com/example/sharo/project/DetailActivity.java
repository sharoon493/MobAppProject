package com.example.sharo.project;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class DetailActivity extends AppCompatActivity{

    Button mail,share;
    TextView Username,phoneNo,Country,City, Email,Description,Reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d("Tagtest","Detail--0.1");
        Username =findViewById(R.id.username);
        phoneNo =findViewById(R.id.mobile_number);
        Country =findViewById(R.id.country);
        City = findViewById(R.id.city);
        Email = findViewById(R.id.email);
        Description =findViewById(R.id.request_desc);
        Reason = findViewById(R.id.reason);
        mail = findViewById(R.id.SendEmail);
        share = findViewById(R.id.Share);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] addresses = {Email.getText().toString()};
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_EMAIL, addresses);
                i.putExtra(Intent.EXTRA_SUBJECT, "OnDonate (Contact)");
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivity(i);
                }
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        Username.setText(getIntent().getStringExtra("Username"));;
        phoneNo.setText(getIntent().getStringExtra("MobileNumber"));
        Country.setText(getIntent().getStringExtra("Country"));
        City.setText(getIntent().getStringExtra("City"));
        Email.setText(getIntent().getStringExtra("Email"));
        Description.setText(getIntent().getStringExtra("Request_Desc"));
        Reason.setText(getIntent().getStringExtra("Reason"));

    }
}
