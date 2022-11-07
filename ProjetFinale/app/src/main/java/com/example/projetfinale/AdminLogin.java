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

public class AdminLogin extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button Login;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        Email = findViewById(R.id.AdminEmail);
        Password = findViewById(R.id.AdminPassword);
        Login =  findViewById(R.id.AdminLogin2);
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
                        Toast.makeText(AdminLogin.this, "Utilisatuer a entrer sans problèmes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminLogin.this, CookerMenu.class));
                    } else {
                        Toast.makeText(AdminLogin.this, "Erreur de login" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    public void OnAdminLogin(View view){
        Intent intent = new Intent(getApplicationContext(), CookerMenu.class);
        startActivityForResult(intent,0);
    }
}
