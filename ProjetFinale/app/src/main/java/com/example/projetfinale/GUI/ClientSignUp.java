package com.example.projetfinale.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.projetfinale.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ClientSignUp extends AppCompatActivity {


    // Instance variables

    private EditText FirstName;
    private EditText LastName;
    private EditText Email;
    private EditText Address;
    private EditText Password;
    FirebaseAuth mAuth;
    private Button clientSignIn;
    FirebaseFirestore db;
    // Instance methods

    // Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sign_up);

        FirstName = findViewById(R.id.firstNameField);
        LastName = findViewById(R.id.lastNameField);
        Email = findViewById(R.id.emailField);
        Password = findViewById(R.id.passwordField);
        Address = findViewById(R.id.addressField);
        clientSignIn = findViewById(R.id.btn_cook_signup);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        clientSignIn.setOnClickListener(view ->{
            createUser();
        });
    }


    private void createUser(){

        String firstName = FirstName.getText().toString();
        String lastName = LastName.getText().toString();
        String email = Email.getText().toString();
        String address = Address.getText().toString();
        String password = Password.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Email.setError("Email ne peut pas être vide");
            Email.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Password.setError("Password ne peut pas être vide");
            Password.requestFocus();
        } else if (TextUtils.isEmpty(firstName)) {
            FirstName.setError("First name ne peut pas être vide");
            FirstName.requestFocus();
        } else if(TextUtils.isEmpty(lastName)) {
            LastName.setError("Last name ne peut pas être vide");
            LastName.requestFocus();
        } else if(TextUtils.isEmpty(address)) {
            Address.setError("Address ne peut pas être vide");
            Address.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ClientSignUp.this, "Utilisatuer crée sans problèmes", Toast.LENGTH_SHORT).show();
                        addClientUser(email,firstName,lastName,address);
                        startActivity(new Intent(ClientSignUp.this, ClientLogin.class));
                    } else {
                        Toast.makeText(ClientSignUp.this, "Erreur de creation" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void addClientUser(String email,String first, String last, String addr){
        // Create a new user with a first and last name
        Map<String, Object> client = new HashMap<>();
        client.put("clientEmail", email);
        client.put("clientFirstname", first);
        client.put("clientLastname", last);
        client.put("clientFullName", first+ " "+last);
        client.put("clientAddress", addr);


        // Add a new document with a generated ID
        db.collection("client")
                .add(client)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erreur de création client:" + e.getMessage(), Toast.LENGTH_SHORT).show();;
                    }
                });

    }

    /*

    public void OnClientSignIn(View view) {

        String firstName = String.valueOf(FirstName.getText());
        String lastName = String.valueOf(LastName.getText());
        String email = String.valueOf(Email.getText());
        String password = String.valueOf(Password.getText());
        String address = String.valueOf(Address.getText());

        // Checking entries
        boolean wrongEntry = false;
        if (firstName == null) {
            FirstName.setTextColor(Color.RED);
            wrongEntry = true;
        }
        if (lastName == null) {
            LastName.setTextColor(Color.RED);
            wrongEntry = true;
        }
        if (email == null) {
            Email.setTextColor(Color.RED);
            wrongEntry = true;
        }
        if (password == null) {
            Password.setTextColor(Color.RED);
            wrongEntry = true;
        }
        if (address == null) {
            Address.setTextColor(Color.RED);
            wrongEntry = true;
        }
        // Extra check for emails
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (pat.matcher(email).matches()) {
            Email.setTextColor(Color.RED);
            wrongEntry = true;
        }

        if (! wrongEntry) {
            try {
                Client client = new Client(firstName, lastName, email, password, address);
                startActivity(new Intent(ClientSignUp.this, ClientMenu.class));
                return;
            } catch (IllegalArgumentException e) {
                wrongEntry = true;
            }
        }

        Toast.makeText(ClientSignUp.this, "Please provide valid entries",
                Toast.LENGTH_LONG).show();

    }

    */

    public void OnClientSignIn(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void OnMainPageFromClientSignUp(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}