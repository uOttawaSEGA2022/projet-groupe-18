package com.example.projetfinale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ClientSignIn extends AppCompatActivity {
    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Address;
    EditText Password;
    FirebaseAuth mAuth;
    Button clientSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_sign_in);

        FirstName = findViewById(R.id.editTextTextPersonName4);
        LastName = findViewById(R.id.editTextTextPersonName7);
        Email = findViewById(R.id.editTextTextPersonName8);
        Address = findViewById(R.id.editTextTextPersonName3);
        Password = findViewById(R.id.editTextTextPersonName5);
        clientSignIn = findViewById(R.id.clientSignIn);

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
        if(TextUtils.isEmpty(email)){
            Email.setError("Email ne peut pas être vide");
            Email.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            Password.setError("Password ne peut pas être vide");
            Password.requestFocus();
        }else if(TextUtils.isEmpty(firstName)){
            FirstName.setError("First name ne peut pas être vide");
            FirstName.requestFocus();
        }else if(TextUtils.isEmpty(lastName)){
            LastName.setError("Last name ne peut pas être vide");
            LastName.requestFocus();
        }else if(TextUtils.isEmpty(address)){
            Address.setError("Address ne peut pas être vide");
            Address.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ClientSignIn.this, "Utilisatuer a entrer sans problèmes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ClientSignIn.this, ClientLogin.class));
                    }else{
                        Toast.makeText(ClientSignIn.this, "Erreur de régistration" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    public void OnClientSignIn(View view){
        Intent intent = new Intent(getApplicationContext(), CookerMenu.class);
        startActivityForResult(intent,0);
    }
}