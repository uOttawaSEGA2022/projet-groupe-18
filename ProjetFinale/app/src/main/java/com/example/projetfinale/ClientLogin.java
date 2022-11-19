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
        setContentView(R.layout.activity_client_login);

        Email = findViewById(R.id.emailField);
        Password = findViewById(R.id.passwordField);
        Login = findViewById(R.id.btn_admin_login);
        mAuth = FirebaseAuth.getInstance();
        Login.setOnClickListener(view -> {
            LoginUser();
        });
    }

    private void LoginUser(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        if(TextUtils.isEmpty(email)){
            Email.setError("E-mail section cannot be empty ");
            Email.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            Password.setError("Password section cannot be empty");
            Password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ClientLogin.this, "User logged in without issues", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ClientLogin.this, ClientWelcome.class));
                    } else {
                        Toast.makeText(ClientLogin.this, "Log in error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
<<<<<<< Updated upstream

    public void OnClientLogin(View view){
        startActivity(new Intent(getApplicationContext(), ClientMenu.class));
    }

    public void OnMainPageFromClientLogin(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
=======
    public void OnLogin(View view){
        Intent intent = new Intent(getApplicationContext(), ClientWelcome.class);
        startActivityForResult(intent,0);
>>>>>>> Stashed changes
    }
}