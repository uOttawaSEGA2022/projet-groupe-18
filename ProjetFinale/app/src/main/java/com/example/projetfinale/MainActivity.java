package com.example.projetfinale;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Client userClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnBienvenue(View view){
        Intent intent = new Intent(getApplicationContext(),Bienvenue.class);
        startActivityForResult(intent,0);
    }

    public void OnAdminLogin(View view){
        Intent intent = new Intent(getApplicationContext(),AdminLogin.class);
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

    public void OnCookSignIn(View view){
        Intent intent = new Intent(getApplicationContext(),CuisinierSignIn.class);
        startActivityForResult(intent,0);
    }

    public void OnCookLogin(View view){
        Intent intent = new Intent(getApplicationContext(),CuisinerLogin.class);
        startActivityForResult(intent,0);
    }

    public void OnDisconect(View view){
        finishAndRemoveTask();
    }
}