package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClientMenu extends AppCompatActivity {
    Button Logout;
    Button Complaint;
    FirebaseAuth mAuth;

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

        Logout = findViewById(R.id.btn_logout);
        Complaint = findViewById(R.id.btn_ComplaintPage);
        mAuth = FirebaseAuth.getInstance();
        Logout.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(ClientMenu.this, MainActivity.class));
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(ClientMenu.this, ClientLogin.class ));
        }
    }
    public void OnComplaint(View view){
        startActivity(new Intent(getApplicationContext(), ClientComplaint.class));
    }
    public void OnLogout(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

}
