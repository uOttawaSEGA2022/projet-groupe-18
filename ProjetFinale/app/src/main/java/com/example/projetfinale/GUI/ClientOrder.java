package com.example.projetfinale.GUI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetfinale.GUI.clientmenu.ClientMenu;
import com.example.projetfinale.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ClientOrder extends AppCompatActivity {
    //GUI variables
    TextView txt_mealFullName;
    TextView txt_mealPrice;
    EditText txt_mealQuantity;

    //Firebase variables
    String clientID;
    String clientFullName ;
    String cookID;
    String cookRestaurantName;
    String mealID;
    String mealFullName ;
    String mealPrice ;
    int mealQuantity;

    FirebaseAuth mAuth;
    FirebaseFirestore db;


    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_order);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        txt_mealFullName = findViewById(R.id.txt_mealName);
        txt_mealPrice = findViewById(R.id.txt_mealPrice);
        txt_mealQuantity = findViewById(R.id.txt_mealQuantity);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            clientID = extras.getString("clientID");
            clientFullName = extras.getString("clientFullName");
            cookID = extras.getString("cookID");
            cookRestaurantName = extras.getString("cookRestaurantName");
            mealID = extras.getString("mealID");
            mealFullName =  extras.getString("mealFullName");
            mealPrice = extras.getString("mealPrice");
            txt_mealFullName.setText(mealFullName);
            txt_mealPrice.setText(mealPrice);
        }
    }

    public void onAction( View view) {
        mealQuantity = Integer.parseInt(txt_mealQuantity.getText().toString());
        if (TextUtils.isEmpty(txt_mealQuantity.getText())) {
            txt_mealQuantity.setError("Quantity can't be empty");
            txt_mealQuantity.requestFocus();
        } else if (mealQuantity == 0) {
            txt_mealQuantity.setError("Quantity can't be set to 0");
            txt_mealQuantity.requestFocus();
        }
        else{
            addOrder();
        }
    }

    private void addOrder()
    {
        // Create a new user with a first and last name
        Map<String, Object> orders = new HashMap<>();
        orders.put("clientID", clientID);
        orders.put("clientFullName" , clientFullName);
        orders.put("cookID", cookID);
        orders.put("cookRestaurantName", cookRestaurantName);
        orders.put("mealFullName", mealFullName);
        orders.put("mealPrice", mealPrice);
        orders.put("mealID" , mealID);
        orders.put("quantity" , mealQuantity);
        orders.put("orderStatus" , "pending");
        orders.put("deliveryTime" , 30 );


        // Add a new document with a generated ID
        db.collection("orders")
                .add(orders)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(), "Order has been completed" , Toast.LENGTH_SHORT).show();
                        finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error while creating:" + e.getMessage(), Toast.LENGTH_SHORT).show();;
                    }
                });

    }
    public void OnReturn( View view) {
        Intent intent = new Intent(getApplicationContext(), ClientMenu.class);
    }
}