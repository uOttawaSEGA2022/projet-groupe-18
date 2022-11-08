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
    }

    public void OnBienvenue(View view) {
        Intent intent = new Intent(getApplicationContext(), CookerMenu.class);
        startActivityForResult(intent, 0);
    }

    public void OnClientLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), ClientLogin.class);
        startActivityForResult(intent, 0);
    }

    public void OnClientSignIn(View view) {
        Intent intent = new Intent(getApplicationContext(), ClientSignIn.class);
        startActivityForResult(intent, 0);
    }

    public void OnCookerSignIn(View view) {
        Intent intent = new Intent(getApplicationContext(), CuisinierSignIn.class);
        startActivityForResult(intent, 0);
    }

    public void OnCookerLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), CuisinerLogin.class);
        startActivityForResult(intent, 0);
    }

    public void OnDisconnect(View view) {finishAndRemoveTask();
    }
    public void OnAdminLogin(View view){
        Intent intent = new Intent(getApplicationContext(),AdminLogin.class);
        startActivityForResult(intent,0);
    }

    private void LoginClient() {
        startActivity(new Intent(MainActivity.this, ClientLogin.class));
    }
    private void SignInClient(){
        startActivity(new Intent(MainActivity.this, ClientSignIn.class));
    }
}