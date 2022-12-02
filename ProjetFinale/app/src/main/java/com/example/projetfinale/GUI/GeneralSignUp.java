package com.example.projetfinale.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projetfinale.R;

public class GeneralSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_sign_up);
    }

    public void OnClientSignUp(View view) {
        startActivity(new Intent(GeneralSignUp.this, ClientSignUp.class));
    }
    public void OnCookSignUp(View view) {
        startActivity(new Intent(GeneralSignUp.this, CookSignUp.class));
    }

    public void OnLoginPage(View view) {
        startActivity(new Intent(GeneralSignUp.this, MainActivity.class));
    }

}