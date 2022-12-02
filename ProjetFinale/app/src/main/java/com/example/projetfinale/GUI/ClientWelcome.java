package com.example.projetfinale.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetfinale.R;

public class ClientWelcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_menu);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Back Arrow functionality coming soon", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView dropdownSettingsIcon = findViewById(R.id.dropdown_settings_icon);
        dropdownSettingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Dropdown settings coming soon", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void OnReturn(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent,0);
    }
}
