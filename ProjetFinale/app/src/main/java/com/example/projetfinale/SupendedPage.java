package com.example.projetfinale;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SupendedPage extends AppCompatActivity {
    TextView SuspensionLength;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabase;
    private String suspension_Length = "testing";
    private String id;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suspended_cook_page);
        mAuth = FirebaseAuth.getInstance();
        usersDatabase = FirebaseDatabase.getInstance().getReference("Users");

        SuspensionLength = findViewById(R.id.SuspensionLength);



    }

    protected void onStart() {
        super.onStart();
        FirebaseUser cook = mAuth.getCurrentUser();
        if(cook != null){
            id = cook.getUid();
            usersDatabase.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    DataSnapshot dataSnapshot = task.getResult();
                    Cook cook = dataSnapshot.getValue(Cook.class);
                    if (cook != null){
                        suspension_Length = String.valueOf(cook.getSuspensionStatus());
                        SuspensionLength.setText(suspension_Length);
                    }
                }
            });

        }
    }
}

