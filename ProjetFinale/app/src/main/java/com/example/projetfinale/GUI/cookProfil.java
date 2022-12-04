package com.example.projetfinale.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetfinale.R;

public class cookProfil extends AppCompatActivity {
    String cookID;
    String cookName;
    String cookEmail;
    String cookRestaurantName;
    String cookFirstName;
    String cookLastName;
    String cookDescription;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_profil);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cookID = extras.getString("cookID");
            cookRestaurantName = extras.getString("cookRestaurantName");

    }
}
}