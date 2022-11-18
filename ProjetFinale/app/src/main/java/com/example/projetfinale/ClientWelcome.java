package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ClientWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);
    }

    public void OnReturn(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent,0);
    }
}
