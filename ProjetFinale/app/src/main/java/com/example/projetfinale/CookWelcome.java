
package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CookWelcome extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    String cookName;
    String cookEmail;
    int cookStatus;
    String cookID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_cook_welcome);

    }
    protected void onStart(){
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(getApplicationContext(), CookLogin.class ));
        } else
        {
            //Get cook username
            cookName = user.getDisplayName();
            cookEmail = user.getEmail();
            //Display cook username on the page
            TextView welcomeMsg = findViewById(R.id.txt_cook_welcomeMsg);
            welcomeMsg.setText("Welcome "+cookName+ " ("+cookEmail+")");
            displayCookInfo();


        }
    }
    public void displayCookInfo(){
        //Get cookID and cookstatus : temporarely suspended =1 , Active = 2, permanently suspend = 3
        String codeStatus[] = {"Not specified","Temporarely suspended","Active","Permenantly suspend"};
        db.collection("cook")
                .whereEqualTo("cookEmail",cookEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cookStatus = Integer.parseInt(document.get("cookStatus").toString());
                                cookID = document.getId().toString();
                            }
                            TextView statusMsg = findViewById(R.id.txt_cook_status);
                            statusMsg.setText("Your account is "+codeStatus[cookStatus]);
                            if (cookStatus!=2){
                                //Deactivate buttons and invisible lists when account is suspended
                                Button btnMeals = findViewById(R.id.btn_cook_manage_meals);
                                btnMeals.setEnabled(Boolean.FALSE);
                                Button btnOrders = findViewById(R.id.btn_cook_manage_orders);
                                btnOrders.setEnabled(Boolean.FALSE);
                                ListView list_cook_menuItems = findViewById(R.id.list_cook_menuItems);
                                list_cook_menuItems.setEnabled(Boolean.FALSE);
                                ListView list_cook_orders = findViewById(R.id.list_cook_orders);
                                list_cook_orders.setVisibility(View.INVISIBLE);
                            }

                            displayMeal(cookID);

                        } else {
                            Toast.makeText(CookWelcome.this, "Database cook infos error"
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void displayMeal(String cookID) {
        //Get the list of all meals of a cook based on Firestore database

        db.collection("meal")
                .whereEqualTo("cookID",cookID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        List<String> lstMeal = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lstMeal.add(document.get("mealName").toString() + " ("
                                        + document.get("mealPrice").toString()
                                        + " $CAD)");

                            }
                            ListView list_cook_menuItems = findViewById(R.id.list_cook_menuItems);
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_activated_1,lstMeal);
                            list_cook_menuItems.setAdapter(arrayAdapter);
                        } else {
                            Toast.makeText(CookWelcome.this, "Database error"
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void OnReturn( View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

    }
    public void OnLogout( View view){

        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void OnManageMeals( View view){
        Intent i = new Intent(getApplicationContext(), CookMeal.class);
        i.putExtra("cookID",cookID);
        startActivity(i);
    }
    public void OnManageOrders( View view){

        Intent i = new Intent(getApplicationContext(), CookOrder.class);
        i.putExtra("CookID",cookID);
        startActivity(i);
    }

}
