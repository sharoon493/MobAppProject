package com.example.sharo.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class FirstActivity extends AppCompatActivity {


    EditText Password,Email;
    Button Submit,Exit;
    TextView AlreadyAccount;
    SharedPreferences p;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    int testbit=0,loginbit =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        getSupportActionBar().hide();

        String temp,temp2;
//        p=  getSharedPreferences("layout",Context.MODE_PRIVATE);
//        temp = p.getString("Email","");
//        if(temp.equals("")){
//            Intent tIntent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(tIntent);
//        }

        mAuth = FirebaseAuth.getInstance();

        Password = findViewById(R.id.EmailSTART);
        Email = findViewById(R.id.UserNameSTART);
        Submit = findViewById(R.id.btnStartFrag);
        Exit = findViewById(R.id.btnExitFrag);
        AlreadyAccount =findViewById(R.id.AlreadyAccount);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        AlreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testbit ==0){
                    testbit=1;
                    Submit.setText("Sign in");
                    AlreadyAccount.setText("Forget Password, Click Here");
                    Exit.setText("Sign up");
                }
                else if(testbit ==1){
                        ResetEmail();
                }

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p=  getSharedPreferences("layout",Context.MODE_PRIVATE);
                SharedPreferences.Editor e =p.edit();
                e.putString("Password",Password.getText().toString());
                e.putString("Email",Email.getText().toString());
                e.commit();
                if(testbit ==0){
                    if (ValidInput() == true){
                        registerUser();
                    }
                }
                else if(testbit ==1){
                    if(ValidInput()==true){
                        userLogin();
                    }

                }
            }
        });
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(testbit ==0){
                    testbit =1;
                    finish();
                    System.exit(0);
                }
                if(testbit ==1){
                    testbit=0;
                    Submit.setText("Sign up");
                    AlreadyAccount.setText("Already, Have an Account?, Click Here");
                    Exit.setText("Exit");
                }

            }
        });
    }

    private void ResetEmail(){
        String email = Email.getText().toString().trim();

        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please enter a valid email");
            Email.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),"Reset your Password from Email",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            // ...
                        }
                    }
                });
    }
    private boolean ValidInput(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if (email.isEmpty()) {
            Email.setError("Email is required");
            Email.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please enter a valid email");
            Email.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            Password.setError("Password is required");
            Password.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            Password.setError("Minimum lenght of password should be 6");
            Password.requestFocus();
            return false;
        }
        return true;
    }
    private void userLogin() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        Submit.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Submit.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void registerUser() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        Submit.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                Submit.setVisibility(View.VISIBLE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Signed in", Toast.LENGTH_SHORT).show();
                    Intent tIntent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(tIntent);
                    finish();
                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                return;
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

}
