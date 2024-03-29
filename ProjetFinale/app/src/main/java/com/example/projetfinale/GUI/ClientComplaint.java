package com.example.projetfinale.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projetfinale.GUI.clientmenu.ClientMenu;
import com.example.projetfinale.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClientComplaint extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView FullName;
    EditText Complaint;
    Button Submit;
    Button Return;
    Spinner spinner;
    String[] spin = {"Admin"};
    ArrayAdapter aa;
    String admin;
    String clientID;
    String clientFullName;
    String cookID;
    String cookRestaurantName;
    String complaintDescription;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_complaints);

        FullName = findViewById(R.id.edt_username);
        Complaint = findViewById(R.id.tctComplaint);
        Submit = findViewById(R.id.ComplaintSubmit);

        spinner = findViewById(R.id.spinner);
        mAuth = FirebaseAuth.getInstance();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            clientID = extras.getString("clientID");
            clientFullName = extras.getString("clientFullName");
            FullName.setText(clientFullName);
        }

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
                    Toast.makeText(ClientComplaint.this, "Field needs to be filled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ClientComplaint.this, "Complaint sent" + admin, Toast.LENGTH_SHORT).show();
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
