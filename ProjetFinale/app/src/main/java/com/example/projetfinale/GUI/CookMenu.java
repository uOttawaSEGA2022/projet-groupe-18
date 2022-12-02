package com.example.projetfinale.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetfinale.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CookMenu extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_cook_welcome);
        Button returnToMain;
        returnToMain = findViewById(R.id.btn_cook_logout);

        returnToMain.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(CookMenu.this, MainActivity.class));
        });
    }
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(CookMenu.this, ClientLogin.class ));
        }
    }

    public void OnReturn( View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent,0);
    }
}