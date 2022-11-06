package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ClientComplaint extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText FullName;
    EditText Complaint;
    Button Submit;
    Button Return;
    Spinner spinner;
    String[] spin = {"Admin"};
    ArrayAdapter aa;
    String admin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_complaints);

        FullName = findViewById(R.id.FullName);
        Complaint = findViewById(R.id.Complaint);
        Submit = findViewById(R.id.ComplaintSubmit);
        Return = findViewById(R.id.ClientReturn);
        spinner = findViewById(R.id.spinner);
        mAuth = FirebaseAuth.getInstance();
        Return.setOnClickListener(view ->{
            mAuth.signOut();
            startActivity(new Intent(ClientComplaint.this, MainActivity.class));
        });
        spinner.setOnItemSelectedListener(this);
        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item,spin);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a1 = FullName.getText().toString();
                String b1 = Complaint.getText().toString();
                if (a1.equals("") || b1.equals("")) {
                    Toast.makeText(ClientComplaint.this, "Erreur, tout doit être remplis", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ClientComplaint.this, "Complainte envoyer" + admin, Toast.LENGTH_SHORT).show();
                   FullName.setText("");
                   Complaint.setText("");
                }
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(ClientComplaint.this, ClientLogin.class ));
        }
    }

    public void OnReturn(View view){
        Intent intent = new Intent(getApplicationContext(), ClientMenu.class);
        startActivityForResult(intent,0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
