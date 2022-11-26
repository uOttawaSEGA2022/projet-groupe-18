package com.example.projetfinale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CookMeal extends AppCompatActivity {
    TextView txtmealName;
    TextView txtmealPrice;
    String cookID;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button btnadd;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_manage_meals);

        ImageView backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Back Arrow functionality coming soon", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView dropdownSettingsIcon = findViewById(R.id.dropdown_settings_icon);
        dropdownSettingsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Dropdown settings coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        txtmealName = findViewById(R.id.txt_mealName);
        txtmealPrice = findViewById(R.id.txt_mealPrice);
        btnadd = findViewById(R.id.btn_add_meal);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cookID = extras.getString("cookID");

        }
    }
    public void onAddMeal( View view) {
            String mealNname = txtmealName.getText().toString();
            String mealPrice = txtmealPrice.getText().toString();
            if (TextUtils.isEmpty(mealNname)) {
                txtmealName.setError("Name can't be empty");
                txtmealName.requestFocus();
            } else if (TextUtils.isEmpty(mealPrice)) {
                txtmealPrice.setError("Price can't be empty");
                txtmealPrice.requestFocus();
            } else {
                double price = Double.parseDouble(mealPrice);
                addCookMeal(cookID,mealNname,price);
            }

    }
    private void addCookMeal(String ID,String name, double price){
        // Create a new user with a first and last name
        Map<String, Object> meal = new HashMap<>();
        meal.put("cookID", ID);
        meal.put("mealName", name);
        meal.put("mealPrice", price);


        // Add a new document with a generated ID
        db.collection("meal")
                .add(meal)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Repas ajouté au menu" , Toast.LENGTH_SHORT).show();
                        txtmealName.setText("");
                        txtmealPrice.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Erreur de création :" + e.getMessage(), Toast.LENGTH_SHORT).show();;
                    }
                });

    }
    public void OnReturn( View view) {
        Intent intent = new Intent(getApplicationContext(), CookWelcome.class);
    }
    }