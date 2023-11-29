package com.example.prototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPassword extends AppCompatActivity {

    private EditText editText1;
    private Button button_reset;
    private Button button_back;
    FirebaseAuth mAuth;
    private String Email;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Objects.requireNonNull(getSupportActionBar()).hide();

        editText1=findViewById(R.id.reset_email);
        button_reset=findViewById(R.id.reset_password);
        button_back=findViewById(R.id.go_back);
        progressBar = findViewById(R.id.progress_forget);

        mAuth=FirebaseAuth.getInstance();

        progressBar.setVisibility(View.GONE);
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                 Email=editText1.getText().toString().trim();

                 new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run() {
                         if(!TextUtils.isEmpty(Email))
                         {
                             ResetPassword();
                         }
                         else {
                             editText1.setError("Email field can't be empty");
                             progressBar.setVisibility(View.GONE);
                         }
                     }
                 },4000);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void ResetPassword() {
        mAuth.sendPasswordResetEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgetPassword.this,"Reset Password link has been sent to your Email registered",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ForgetPassword.this,SignInActivity.class);
                 startActivity(intent);
                 finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgetPassword.this,"Error :-"+ e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}