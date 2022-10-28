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

public class ClientLogin extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button Login;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientlogin);

        Email = findViewById(R.id.Email3);
        Password = findViewById(R.id.MDP2);
        Login = findViewById(R.id.Login2);
        mAuth = FirebaseAuth.getInstance();
        Login.setOnClickListener(view -> {
            LoginUser();
        });
    }
    private void LoginUser(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        if(TextUtils.isEmpty(email)){
            Email.setError("Email ne peut pas être vide");
            Email.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            Password.setError("Password ne peut pas être vide");
            Password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ClientLogin.this, "Utilisatuer a entrer sans problèmes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ClientLogin.this, Bienvenue.class));
                    } else {
                        Toast.makeText(ClientLogin.this, "Erreur de login" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    public void OnLogin(View view){
        Intent intent = new Intent(getApplicationContext(),Bienvenue.class);
        startActivityForResult(intent,0);
    }
}