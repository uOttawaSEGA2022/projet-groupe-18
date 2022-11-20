package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CookLogin extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button Login;
    FirebaseAuth mAuth;
    String cookUserName;
    public String getCookUserName(){
        return cookUserName;
    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_login);

        Email = findViewById(R.id.emailField);
        Password = findViewById(R.id.passwordField);
        Login = findViewById(R.id.btn_cook_login);
        mAuth = FirebaseAuth.getInstance();
        Login.setOnClickListener(view -> {
            LoginUser();
        });
    }
    private void LoginUser(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        if(TextUtils.isEmpty(email)){
            Email.setError("E-mail section cannot be empty");
            Email.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            Password.setError("Password section cannot be empty");
            Password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(CookLogin.this, "User logged in without errors", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CookLogin.this, CookWelcome.class);
                        i.putExtra("cookUserName",email);
                        startActivity(i);
                    } else {
                        Toast.makeText(CookLogin.this, "Login error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        };
    }

    public void OnCookLogin(View view) {
        startActivity(new Intent(getApplicationContext(), CookWelcome.class));
    }

    public void OnMainPageFromCookLogin(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}