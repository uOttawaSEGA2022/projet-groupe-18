
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CookWelcome extends AppCompatActivity {
    String cookUserName="";
    int cookStatus;
    int cookID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_cook_welcome);
        //Get cook username
        Bundle extras = getIntent().getExtras();
        cookUserName = extras.getString("cookUserName");
        //Display cook username on the page
        TextView welcomeMsg = findViewById(R.id.txt_cook_welcomeMsg);
        welcomeMsg.setText("Welcome Cook "+cookUserName);
        //Get cookID and cookstatus : temporarely suspended =1 , Active = 2, permanently suspend = 3
        String codeStatus[] = {"Not specified","Temporarely suspended","Active","Permenantly suspend"};
        db.collection("cook")
                .whereEqualTo("cookEmail",cookUserName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                       if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                cookStatus = Integer.parseInt(document.get("cookStatus").toString());
                                cookID = Integer.parseInt(document.get("cookID").toString());
                            }
                           TextView statusMsg = findViewById(R.id.txt_cook_status);
                           statusMsg.setText("Your account ID "+ cookID + " is "+codeStatus[cookStatus]);
                            if (cookStatus!=2){
                                //Deactivate buttons and invisible lists when account is suspended
                                Button btnMeals = findViewById(R.id.btn_cook_manage_meals);
                                btnMeals.setEnabled(Boolean.FALSE);
                                Button btnOrders = findViewById(R.id.btn_cook_manage_orders);
                                btnOrders.setEnabled(Boolean.FALSE);
                                ListView list_cook_menuItems = findViewById(R.id.list_cook_menuItems);
                                list_cook_menuItems.setVisibility(View.INVISIBLE);
                                ListView list_cook_orders = findViewById(R.id.list_cook_orders);
                                list_cook_orders.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            Toast.makeText(CookWelcome.this, "Database cook infos error"
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Get the list of all meals of a cook based on Firestore database

        db.collection("meal")
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

    public void OnReturn(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent,0);
    }

}
