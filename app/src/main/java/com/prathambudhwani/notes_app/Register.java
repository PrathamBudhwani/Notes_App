package com.prathambudhwani.notes_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    TextInputEditText edtemail, edtpassword;
    TextView loginnow;
    Button btnregister;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent inextMain = new Intent(Register.this, MainActivity.class);
            startActivity(inextMain);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edtemail = findViewById(R.id.edtemail);
        edtpassword = findViewById(R.id.edtpassword);
        loginnow = findViewById(R.id.loginow);
        btnregister = findViewById(R.id.btnregister);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);

        loginnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inextlogin = new Intent(Register.this, LogIn.class);
                startActivity(inextlogin);
                finish();
            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                progressBar.setVisibility(View.VISIBLE);
                email = String.valueOf(edtemail.getText());
                password = String.valueOf(edtpassword.getText());
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Register.this, "Enter an email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    Intent ilogin = new Intent(Register.this, LogIn.class);
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(ilogin);
                                            finish();
                                        }
                                    }, 500);


                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });


            }
        });
    }
}