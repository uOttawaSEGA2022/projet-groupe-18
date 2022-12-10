package com.example.projetfinale.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetfinale.R;
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
    RadioButton rdAvailableYes;
    String action;
    String cookID;
    String cookRestaurantName;
    String mealID;
    String mealName ;
    String mealPrice ;
    Boolean checkMealAvailable;
    Integer mealAvailable;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button btnaction;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_manage_meals);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        txtmealName = findViewById(R.id.txt_mealName);
        txtmealPrice = findViewById(R.id.txt_mealPrice);
        rdAvailableYes= findViewById(R.id.rdYes);
        btnaction = findViewById(R.id.btnaction);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            action =  extras.getString("action");
            cookID = extras.getString("cookID");
            cookRestaurantName = extras.getString("cookRestaurantName");
            btnaction.setText(action);
        }
        if (action.equals("Update"))
        {
            mealID = extras.getString("mealID");
            mealName =  extras.getString("mealName");
            mealPrice = extras.getString("mealPrice");
            mealAvailable = extras.getInt("mealAvailable");
            checkMealAvailable = mealAvailable==1;
            txtmealName.setText(mealName);
            txtmealPrice.setText(mealPrice);
            rdAvailableYes.setChecked(checkMealAvailable);

        }
    }
    public void onAction( View view) {
        mealName = txtmealName.getText().toString();
        mealPrice = txtmealPrice.getText().toString();
        checkMealAvailable = rdAvailableYes.isChecked();

        if (checkMealAvailable){
            mealAvailable=1;
        }else
        {
            mealAvailable=0;
        }

        if (TextUtils.isEmpty(mealName)) {
            txtmealName.setError("Name can't be empty");
            txtmealName.requestFocus();
        } else if (TextUtils.isEmpty(mealPrice)) {
            txtmealPrice.setError("Price can't be empty");
            txtmealPrice.requestFocus();
        } else {
            double price = Double.parseDouble(mealPrice);
            if (action.equals("Add")) {
                addCookMeal(cookID, mealName, price, mealAvailable);
                finish();
            }
            if (action.equals("Update")){
                updCookMeal(mealID, mealName, price, mealAvailable);
                finish();

            }
        }

    }
    private void updCookMeal(String MealID,String name, double price, Integer avail){
        db.collection("meal").document(MealID)
                .update("mealName", mealName,
                        "mealPrice",mealPrice,
                        "mealAvailable",mealAvailable);


    }
    private void addCookMeal(String ID,String name, double price, Integer avail){
        // Create a new user with a first and last name
        Map<String, Object> meal = new HashMap<>();
        meal.put("cookID", ID);
        meal.put("cookRestaurantName", cookRestaurantName);
        meal.put("mealName", name);
        meal.put("mealPrice", price);
        meal.put("mealAvailable",avail);


        // Add a new document with a generated ID
        db.collection("meal")
                .add(meal)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Repas ajouté au menu" , Toast.LENGTH_SHORT).show();

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