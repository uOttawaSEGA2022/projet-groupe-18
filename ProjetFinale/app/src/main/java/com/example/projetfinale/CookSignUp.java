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

public class CookSignUp extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Address;
    EditText Password;
    FirebaseAuth mAuth;
    Button CuisinierSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_sign_up);

        FirstName = findViewById(R.id.editTextTextPersonName4);
        LastName = findViewById(R.id.editTextTextPersonName7);
        Email = findViewById(R.id.editTextTextPersonName8);
        Address = findViewById(R.id.editTextTextPersonName3);
        Password = findViewById(R.id.editTextTextPersonName5);
        CuisinierSignIn = findViewById(R.id.clientSignIn);

        mAuth = FirebaseAuth.getInstance();
        CuisinierSignIn.setOnClickListener(view ->{
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
            Email.setError("Email can't be empty");
            Email.requestFocus();
        }else if(TextUtils.isEmpty(password)){
            Password.setError("Password can't be empty");
            Password.requestFocus();
        }else if(TextUtils.isEmpty(firstName)){
            FirstName.setError("First name can't be empty");
            FirstName.requestFocus();
        }else if(TextUtils.isEmpty(lastName)){
            LastName.setError("Last name can't be empty");
            LastName.requestFocus();
        }else if(TextUtils.isEmpty(address)){
            Address.setError("Address can't be empty");
            Address.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(CookSignUp.this, "Utilisatuer a entrer sans problèmes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CookSignUp.this, CuisinerLogin.class));
                    }else{
                        Toast.makeText(CookSignUp.this, "Erreur de régistration" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    public void OnCookSignIn(View view){
        Intent intent = new Intent(CookSignUp.this, CookerMenu.class);
    }
}
}