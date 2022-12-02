package com.example.projetfinale.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projetfinale.GUI.MainActivity;
import com.example.projetfinale.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_welcome);
    }

    public void OnReturn(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent,0);
    }
}
