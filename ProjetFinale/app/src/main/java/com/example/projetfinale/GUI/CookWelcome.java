
package com.example.projetfinale.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetfinale.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CookWelcome extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Switch swmealavail;
    ListView list_cook_menuItems;
    List<String> lstMealID = new ArrayList<>();
    List<String> lstMealName = new ArrayList<>();
    List<String> lstMealPrice = new ArrayList<>();
    List<Integer> lstMealAvailable = new ArrayList<>();
    ListView list_cook_orders;
    List<String> lstOrderID = new ArrayList<>();
    String cookName;
    String cookEmail;
    String cookRestaurantName;
    String cookFirstName;
    String cookLastName;
    String cookDescription;
    int cookStatus;
    int includeMeal=1;
    String cookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_welcome);
        swmealavail = findViewById(R.id.swPastComplaints);
        list_cook_menuItems = findViewById(R.id.list_cook_meals);
        list_cook_orders = findViewById(R.id.list_cook_orders);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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
            cookEmail = user.getEmail().toLowerCase();
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
                                cookID = document.getId().toString();
                                cookStatus = Integer.parseInt(document.get("cookStatus").toString());
                                cookFirstName = document.get("cookFirstname").toString();
                                cookLastName = document.get("cookLastname").toString();
                                cookDescription = document.get("cookDescription").toString();
                                cookRestaurantName = document.get("cookRestaurantName").toString();
                            }
                            TextView statusMsg = findViewById(R.id.txt_cook_status);
                            statusMsg.setText("Your account is "+codeStatus[cookStatus]);
                            if (cookStatus!=2){
                                //Deactivate buttons and invisible lists when account is suspended
                                TableRow btnMeals = findViewById(R.id.trMeals);
                                btnMeals.setEnabled(Boolean.FALSE);
                                TableRow btnOrders = findViewById(R.id.trOrders);
                                btnOrders.setEnabled(Boolean.FALSE);
                                list_cook_menuItems.setEnabled(Boolean.FALSE);
                                list_cook_orders.setEnabled(Boolean.FALSE);
                            }

                            displayMeal(cookID);
                            displayOrders(cookID);

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
                .whereEqualTo("cookID", cookID)
                .whereGreaterThanOrEqualTo("mealAvailable",includeMeal)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        List<String> lstMeal = new ArrayList<>();
                        lstMealID.clear();
                        lstMealName.clear();
                        lstMealPrice.clear();
                        lstMealAvailable.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lstMeal.add(document.get("mealName").toString() + " ("
                                        + document.get("mealPrice").toString()
                                        + " $CAD)");
                                lstMealID.add(document.getId());
                                lstMealName.add(document.get("mealName").toString());
                                lstMealPrice.add(document.get("mealPrice").toString());
                                lstMealAvailable.add(Integer.parseInt(document.get("mealAvailable").toString()));

                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),
                                    android.R.layout.simple_list_item_activated_1,lstMeal);
                            list_cook_menuItems.setAdapter(arrayAdapter);
                        } else {
                            Toast.makeText(CookWelcome.this, "Database error"
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void displayOrders(String cookID) {
        //Get the list of all meals of a cook based on Firestore database
        db.collection("orders")
                .whereEqualTo("cookID",cookID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        List<String> lstOrders = new ArrayList<>();
                        lstOrderID.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lstOrders.add(document.get("mealFullName").toString()
                                        + " X " + document.get("quantity").toString()
                                        + "\n\tFor " + document.get("clientFullName").toString()
                                        + "\n\tStatus: " + document.get("orderStatus"));
                                lstOrderID.add(document.getId());
                            }
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),
                                    android.R.layout.simple_list_item_activated_1,lstOrders);
                            list_cook_orders.setAdapter(arrayAdapter);
                        } else {
                            Toast.makeText(CookWelcome.this, "Database error"
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void OnReturn( View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

    }

    public void OnAddMeals( View view){
        Intent i = new Intent(getApplicationContext(), CookMeal.class);
        i.putExtra("action","Add");
        i.putExtra("cookID",cookID);
        i.putExtra("cookRestaurantName", cookRestaurantName);
        startActivity(i);
        displayMeal(cookID);
    }

    public void OnUpdMeals( View view){
        int position = list_cook_menuItems.getCheckedItemPosition();
        if (position==-1) {
            Toast.makeText(getApplicationContext(), "Please select a meal", Toast.LENGTH_SHORT).show();

        } else {
            String mealID = lstMealID.get(position);
            String mealName = lstMealName.get(position);
            String mealPrice = lstMealPrice.get(position);
            Integer mealAvailable = lstMealAvailable.get(position);
            Intent i = new Intent(getApplicationContext(), CookMeal.class);
            i.putExtra("action","Update");
            i.putExtra("cookID",cookID);
            i.putExtra("cookRestaurantName", cookRestaurantName);
            i.putExtra("mealID",mealID);
            i.putExtra("mealName",mealName);
            i.putExtra("mealPrice",mealPrice);
            i.putExtra("mealAvailable",mealAvailable);
            startActivity(i);
            displayMeal(cookID);
    }
    }
    public void OnDelMeals( View view){
        int position = list_cook_menuItems.getCheckedItemPosition();
        if (position==-1) {
            Toast.makeText(getApplicationContext(), "Please select a meal", Toast.LENGTH_SHORT).show();

        } else {
            String mealID = lstMealID.get(position);
            db.collection("meal").document(mealID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Meal deleted", Toast.LENGTH_SHORT).show();
                        displayMeal(cookID);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        }
    }
    public void OnCheckAvailable( View view){
        includeMeal=1-includeMeal;
        displayMeal(cookID);
    }
    public void OnLogout( View view){

        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void OnProfileUpdate( View view){
        Intent i = new Intent(getApplicationContext(), cookProfil.class);
        i.putExtra("cookID",cookID);
        i.putExtra("cookFirstname",cookFirstName);
        i.putExtra("cookLastname",cookLastName);
        i.putExtra("cookEmail",cookEmail);
        i.putExtra("cookDescription",cookDescription);
        i.putExtra("cookStatus", cookStatus);
        i.putExtra("cookRestaurantName", cookRestaurantName);

        startActivity(i);
    }
    /*
    public void OnOrdAppr(){
        int position = list_cook_orders.getCheckedItemPosition();
        if (position==-1) {
            Toast.makeText(getApplicationContext(), "Please select a meal", Toast.LENGTH_SHORT).show();

        } else {

    }*/
    public void OnManageOrders( View view){

        Intent i = new Intent(getApplicationContext(), CookOrder.class);
        i.putExtra("CookID",cookID);
        i.putExtra("cookRestaurantName", cookRestaurantName);
        startActivity(i);
    }

}
