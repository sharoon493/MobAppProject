package com.example.sharo.project;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

public class FormActivity extends AppCompatActivity implements Myinterface2 {

    RequestForm rfFrag;
    DonateformFragment DfFrag;
    private DatabaseReference myRef;
    long size;
    String FileName;
    public Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage;
    StorageReference storageReference;
    SharedPreferences p;
    FirebaseUser currentFirebaseUser;
    String downUri=null;

    int Tab_bit =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().hide();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        Toast.makeText(this, "" + currentFirebaseUser.getUid(), Toast.LENGTH_SHORT).show();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        Tab_bit = getIntent().getIntExtra("tab_bit",-1);
        size = getIntent().getIntExtra("Size#", -1);
        size++;

        rfFrag = (RequestForm) getSupportFragmentManager().findFragmentById(R.id.ReqFormFrag);
        DfFrag = (DonateformFragment) getSupportFragmentManager().findFragmentById(R.id.DonateFormFrag);
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        p=  getSharedPreferences("layout",Context.MODE_PRIVATE);

        myRef = FirebaseDatabase.getInstance().getReference().child("Users");




        if(Tab_bit ==1){
            fragmentTransaction.hide(rfFrag);
            fragmentTransaction.commit();

            String temp = p.getString("Email","");
            DfFrag.Username.setText(p.getString("Username",""));
            DfFrag.Email.setText(temp);
            DfFrag.MobileNumber.setText(p.getString("MobileNumber",""));

            DfFrag.AddLocation.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });

        }else{

            fragmentTransaction.hide(DfFrag);
            fragmentTransaction.commit();

            String temp = p.getString("Email","");
            rfFrag.Username.setText(p.getString("Username",""));
            rfFrag.Email.setText(temp);
            rfFrag.MobileNumber.setText(p.getString("MobileNumber",""));
            rfFrag.City.setText(p.getString("City",""));
            rfFrag.Country.setText(p.getString("Country","Country"));

            rfFrag.Country.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    CountryList fragment = new CountryList();
                    fragment.show(fragmentTransaction, "frag");
                }
            });
            rfFrag.Reason.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    TagReason fragment = new TagReason();
                    fragment.show(fragmentTransaction, "frag");
                }
            });

        }
        Log.d("Tagtest", "Raising Request3.1");
    }

    @Override
    public void ButtonClicked(String text) {
        if (text.equals("SubmitRequest")) {
            if (validInput() && Tab_bit ==0) {
                FileName =  UUID.randomUUID().toString();
                SharedPreferences.Editor e = p.edit();
                e.putString("Username", rfFrag.Username.getText().toString());
                e.putString("MobileNumber", rfFrag.MobileNumber.getText().toString());
                e.putString("Country", rfFrag.Country.getText().toString());
                e.putString("City", rfFrag.City.getText().toString());
                e.commit();

                String addressImage="null";
                if (rfFrag.testbit == 0) {
                    finish();
                } else {
                    uploadImage();
                    addressImage = "images/"+FileName;

                }

                Requests r = new Requests(size,rfFrag.Username.getText().toString(),
                            rfFrag.Email.getText().toString(), rfFrag.Request_Desc.getText().toString(),
                            rfFrag.Reason.getText().toString(), rfFrag.MobileNumber.getText().toString(),
                            rfFrag.City.getText().toString(), rfFrag.Country.getText().toString(),addressImage);

                myRef.child(currentFirebaseUser.getUid()).child("Requests").child(FileName).setValue(r);

                Toast.makeText(getApplicationContext(), "Your Request Submitted", Toast.LENGTH_SHORT).show();
                finish();

            }
            else if (validInput() && Tab_bit ==1) {
                FileName =  UUID.randomUUID().toString();
                SharedPreferences.Editor e = p.edit();
                e.putString("Username", DfFrag.Username.getText().toString());
                e.putString("MobileNumber", DfFrag.MobileNumber.getText().toString());
                e.putString("Country", DfFrag.Country.getText().toString());
                e.putString("City", DfFrag.City.getText().toString());
                e.commit();

                String addressImage="null";
                if (rfFrag.testbit == 0) {
                    finish();
                } else {
                    uploadImage();
                    addressImage = "images/"+FileName;

                }

//                Donations d = new Donations(long Donate_id,String UserName,String Email,String Donate_Descr,String Condition,
//                        String MobileNumber,double Longitude,double Latitude,String Category, String Image);

                //myRef.child(currentFirebaseUser.getUid()).child("Requests").child(FileName).setValue(r);

                Toast.makeText(getApplicationContext(), "Your Request Submitted", Toast.LENGTH_SHORT).show();
                finish();

            }

        } else if (text.equals("AddImage")) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        0331);
            }
            ShowAlertDialog(getCurrentFocus());

        } else if (text.equals("AddImage2")) {
            chooseImage();
        }

    }

    @Override
    public void Country(String text) {
        rfFrag.Country.setText(text);
        rfFrag.Country.setTextColor(this.getResources().getColor(R.color.white));

    }

    @Override
    public void data(String key, String data) {
        if(key.equals("Reason")){
            rfFrag.Reason.setText(data);
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+FileName);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                rfFrag.imgView.setVisibility(View.GONE);
                rfFrag.AddedPicLayout.setVisibility(View.VISIBLE);
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                rfFrag.imgView2.setImageBitmap(bitmap);
                rfFrag.Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rfFrag.AddedPicLayout.setVisibility(View.GONE);
                        rfFrag.imgView.setVisibility(View.VISIBLE);
                        rfFrag.testbit =0;
                    }
                });
                rfFrag.Retry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chooseImage(); // Choosing Image from Here
                    }
                });

            }
            catch (IOException e)
            {
                Log.e("Tagtest",""+e);
            }
        }else if (requestCode == 589 && resultCode==RESULT_OK ){

            Bitmap bitmap = (Bitmap)data.getExtras().get("data") ;

            rfFrag.imgView.setVisibility(View.GONE);
            rfFrag.AddedPicLayout.setVisibility(View.VISIBLE);
            rfFrag.imgView2.setImageBitmap(bitmap);
            rfFrag.Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rfFrag.AddedPicLayout.setVisibility(View.GONE);
                    rfFrag.imgView.setVisibility(View.VISIBLE);
                    rfFrag.testbit =0;
                }
            });
            rfFrag.Retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowAlertDialog(v); // Choosing Image from Here
                }
            });

            filePath = getImageUri(this,bitmap);

//            Log.e("Tagtest",""+uri);
//            StorageReference filepath = FirebaseStorage.getInstance().getReference().child("photos").child(FileName);
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getApplicationContext(),"Uploading Finished ...",Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
    private boolean validInput(){

        if(rfFrag.Username.getText().toString().isEmpty()){
            rfFrag.Username.setError("Required Field");
            rfFrag.Username.requestFocus();
            return false;
        }
        if(rfFrag.Request_Desc.getText().toString().isEmpty()){
            rfFrag.Request_Desc.setError("Required Field");
            rfFrag.Request_Desc.requestFocus();
            return false;
        }
        if (rfFrag.Email.getText().toString().isEmpty()) {
            rfFrag.Email.setError("Email is required");
            rfFrag.Email.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(rfFrag.Email.getText().toString()).matches()) {
            rfFrag.Email.setError("Please enter a valid email");
            rfFrag.Email.requestFocus();
            return false;
        }
        return true;
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = "";
        try {
            path =
                    MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,
                            "Title", null);
            return Uri.parse(path);
        }catch (Exception e){
            Log.e("Tagtest",""+e);
        }
        return Uri.parse(path);
    }
    public void ShowAlertDialog(View view){

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setTitle("Upload Pitcure From");
        alertdialog.setMessage("Choose an Option");
        alertdialog.setIcon(R.drawable.camera);
        alertdialog.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 589);
            }
        });

        alertdialog.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooseImage();
            }
        });

        alertdialog.show();

    }

}
