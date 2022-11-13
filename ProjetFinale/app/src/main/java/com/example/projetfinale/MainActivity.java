package com.example.projetfinale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button ClientLogin;
    Button ClientSignIn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*      The text commented out has bugs
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClientLogin = findViewById(R.id.LCC);
        ClientSignIn = findViewById(R.id.Client);
        mAuth = FirebaseAuth.getInstance();
        ClientLogin.setOnClickListener(view -> {
            LoginClient();
        });
        ClientSignIn.setOnClickListener(view -> {
            SignInClient();
        });
        */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClientLogin(View view) {
        startActivity(new Intent(MainActivity.this, ClientLogin.class));
    }

    public void OnCookLogin(View view) {
        startActivity(new Intent(MainActivity.this, CuisinerLogin.class));
    }

    public void OnAdminLogin(View view){
        Intent intent = new Intent(getApplicationContext(),AdminLogin.class);
        MainActivity.this.startActivity(intent);
    }

    public void OnSignUpPage(View view) {
        startActivity(new Intent(MainActivity.this, GeneralSignUp.class));
    }

    public void OnDisconnect(View view) {
        finishAndRemoveTask();
    }
}