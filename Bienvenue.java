package com.example.projetfinale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Bienvenue extends AppCompatActivity {
    Button returnToMain;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenue);

        returnToMain = findViewById(R.id.returnToMain);
        mAuth = FirebaseAuth.getInstance();
        returnToMain.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(Bienvenue.this, MainActivity.class));
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(Bienvenue.this, ClientLogin.class ));
        }
    }

    public void OnReturn(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent,0);
    }
}