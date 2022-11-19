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

public class CookSignUp extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Address;
    EditText Password;
    EditText Description;
    FirebaseAuth mAuth;
    Button CookSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_sign_up);

        FirstName = findViewById(R.id.firstNameField);
        LastName = findViewById(R.id.lastNameField);
        Email = findViewById(R.id.emailField);
        Address = findViewById(R.id.passwordField);
        Password = findViewById(R.id.addressField);
        Description = findViewById(R.id.descriptionField);
        CookSignIn = findViewById(R.id.btn_admin_login);

        mAuth = FirebaseAuth.getInstance();
        CookSignIn.setOnClickListener(view ->{
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
                        startActivity(new Intent(CookSignUp.this, CookLogin2.class));
                    }else{
                        Toast.makeText(CookSignUp.this, "Erreur de régistration" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    /*
    public void OnCookSignIn(View view){
        startActivity(new Intent(CookSignUp.this, CookMenu.class));
    }
    */

    public void OnLoginPage(View view) {
        startActivity(new Intent(CookSignUp.this, MainActivity.class));
    }

}
