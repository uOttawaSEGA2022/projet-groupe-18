package com.example.projetfinale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CuisinerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisiner_login);
    }

    public void OnCuisinierLogin(View view){
        Intent intent = new Intent(getApplicationContext(),Bienvenue.class);
        startActivityForResult(intent,0);
    }
}