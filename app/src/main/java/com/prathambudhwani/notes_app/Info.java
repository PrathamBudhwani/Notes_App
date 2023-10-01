package com.prathambudhwani.notes_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Info extends AppCompatActivity {
    TextView user_details;
    Button signout;
    FirebaseAuth auth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        user_details = findViewById(R.id.user_details);
        signout = findViewById(R.id.outsign);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        if (user == null) {
            Intent intent = new Intent(Info.this, LogIn.class);
            startActivity(intent);
            finish();
        } else {
            user_details.setText(user.getEmail());
        }

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Info.this, LogIn.class);
                startActivity(intent);
                finish();

            }
        });

    }
}