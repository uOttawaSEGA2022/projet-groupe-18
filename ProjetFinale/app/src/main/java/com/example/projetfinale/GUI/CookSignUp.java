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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CookSignUp extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Email;
    EditText Address;
    EditText Password;
    EditText Restaurant;
    EditText Description;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button CookSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_sign_up);

        FirstName = findViewById(R.id.firstNameField);
        LastName = findViewById(R.id.lastNameField);
        Email = findViewById(R.id.emailField);
        Password = findViewById(R.id.passwordField);
        Restaurant= findViewById(R.id.RestaurantName);
        Address = findViewById(R.id.addressField);
        Description = findViewById(R.id.descriptionField);
        CookSignIn = findViewById(R.id.btn_cook_signup);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        CookSignIn.setOnClickListener(view ->{
            createUser();
        });
    }

    private void createUser(){
        String firstName = FirstName.getText().toString();
        String lastName = LastName.getText().toString();
        String email = Email.getText().toString().toLowerCase();
        String address = Address.getText().toString();
        String restaurant = Restaurant.getText().toString();
        String password = Password.getText().toString();
        String description = Description.getText().toString();
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
        }else if(TextUtils.isEmpty(restaurant)){
            LastName.setError("Restaurant can't be empty");
            Restaurant.requestFocus();
        }else if(TextUtils.isEmpty(address)){
            Address.setError("Address can't be empty");
            Address.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(CookSignUp.this, "Utilisateur a ete creer sans problèmes", Toast.LENGTH_SHORT).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(firstName+" "+lastName)
                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        addCookUser(email,firstName,lastName,restaurant,address,description);

                                    }
                                });
                        startActivity(new Intent(CookSignUp.this, CookLogin.class));
                    }else{
                        Toast.makeText(CookSignUp.this, "Erreur de création" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }


    public void OnCookSignUp(View view){
        startActivity(new Intent(CookSignUp.this, CookMenu.class));
    }


    public void OnLoginPage(View view) {
        startActivity(new Intent(CookSignUp.this, MainActivity.class));
    }
    private void addCookUser(String email,String first, String last, String resto, String addr, String desc){
        // Create a new user with a first and last name
        Map<String, Object> cook = new HashMap<>();
        cook.put("cookEmail", email);
        cook.put("cookFirstname", first);
        cook.put("cookLastname", last);
        cook.put("cookRestaurantName", resto);
        cook.put("cookAddress", addr);
        cook.put("cookDescription", desc);
        cook.put("cookStatus",2);

        // Add a new document with a generated ID
        db.collection("cook")
                .add(cook)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CookSignUp.this, "Erreur de création :" + e.getMessage(), Toast.LENGTH_SHORT).show();;
                    }
                });

    }

}
