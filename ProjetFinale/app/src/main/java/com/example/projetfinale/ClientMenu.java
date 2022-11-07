package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Intent intent =  new Intent(getApplicationContext(), ClientComplaint.class);
        startActivityForResult(intent, 0);
    }
    public void OnLogout(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent,0);
    }
}
