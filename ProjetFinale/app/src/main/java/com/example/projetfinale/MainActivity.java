package com.example.projetfinale;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnBienvenue(View view){
        Intent intent = new Intent(getApplicationContext(),Bienvenue.class);
        startActivityForResult(intent,0);
    }

    public void OnClientLogin(View view){
        Intent intent = new Intent(getApplicationContext(),ClientLogin.class);
        startActivityForResult(intent,0);
    }

    public void OnClientSignIn(View view){
        Intent intent = new Intent(getApplicationContext(),ClientSignIn.class);
        startActivityForResult(intent,0);
    }

    public void OnCookerSignIn(View view){
        Intent intent = new Intent(getApplicationContext(),CuisinierSignIn.class);
        startActivityForResult(intent,0);
    }

    public void OnCookerLogin(View view){
        Intent intent = new Intent(getApplicationContext(),CuisinerLogin.class);
        startActivityForResult(intent,0);
    }
}