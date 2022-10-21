package com.example.projetfinale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CuisinierSignIn extends AppCompatActivity {
    private Cooker userCooker;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisinier_sign_in);
    }

    public void OnCuisinierSignIn(View view){
        Intent intent = new Intent(getApplicationContext(),Bienvenue.class);
        Intent returnCookerIntent = new Intent();
        userCooker = new Cooker(firstName,lastName,password,email,address);
        returnCookerIntent.putExtra("", userCooker.getPassword());
        setResult(RESULT_OK,returnCookerIntent);
        startActivityForResult(intent,0);
    }
}