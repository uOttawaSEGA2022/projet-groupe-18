
package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

    public static List<String> lstMeal = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_welcome);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //List all meals of a cook based on Firestore database

        db.collection("meal")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete( @NonNull Task<QuerySnapshot> task ) {
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
