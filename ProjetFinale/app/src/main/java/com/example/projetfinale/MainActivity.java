package com.example.projetfinale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    public static String str_userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClientLoginPage(View view) {
        startActivity(new Intent(getApplicationContext(), ClientLogin.class));
    }

    public void OnCookLoginPage(View view) {
        startActivity(new Intent(getApplicationContext(), CookLogin.class));
    }

    public void OnAdminLoginPage(View view){
        startActivity(new Intent(getApplicationContext(), AdminLogin.class));
    }

    public void OnSignUpPage(View view) {
        startActivity(new Intent(getApplicationContext(), GeneralSignUp.class));
    }

    public void OnDisconnect(View view) {
        finishAndRemoveTask();
    }
}