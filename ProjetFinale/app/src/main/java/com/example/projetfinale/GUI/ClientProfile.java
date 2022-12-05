package com.example.projetfinale.GUI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.projetfinale.R;

public class ClientProfile extends AppCompatActivity {
    String clientID;
    String clientName;
    String cookEmail;
    String cookFirstName;
    String cookLastName;
    String cookFullName;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            clientID = extras.getString("clientID");

        }
    }
}
