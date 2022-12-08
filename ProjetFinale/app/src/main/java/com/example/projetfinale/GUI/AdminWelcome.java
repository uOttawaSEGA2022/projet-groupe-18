package com.example.projetfinale.GUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminWelcome extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    Integer IncludePast=1;
    List<String> lstCook= new ArrayList<>();
    List<String> lstComp= new ArrayList<>();
    ListView list_admin_complaints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        list_admin_complaints = findViewById(R.id.list_admin_complaints);
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
            displayComplaints();
        }
    }
    public void displayComplaints() {
        //Get the list of all meals of a cook based on Firestore database
        db.collection("complaint")
                .whereLessThanOrEqualTo("complaintStatus", IncludePast )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete( @NonNull Task<QuerySnapshot> task ) {
                        String StatusCode[] = {"Not available","New complaint","Cook excuse", "Cook suspended", "Cook terminated"};
                        lstCook.clear();
                        lstComp.clear();
                        List<String> lstComplaints = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                lstComp.add(document.getId());
                                lstCook.add(document.get("cookID").toString());
                                lstComplaints.add(document.get("cookRestaurantName").toString()
                                        + "\n\tSent by: " + document.get("clientFullname").toString()
                                        + "\n\tMessage: " + document.get("complaintDescription").toString()
                                        + "\n\tStatus: " + StatusCode[Integer.parseInt(document.get("complaintStatus").toString())]);                                ;
                            }

                            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),
                                    android.R.layout.simple_list_item_activated_1,lstComplaints);
                            list_admin_complaints.setAdapter(arrayAdapter);
                        } else {
                            Toast.makeText(AdminWelcome.this, "Database error"
                                    + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void OnLogout( View view){
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
    public void OnProfileUpdate( View view){

    }
    public void OnExcuse( View view){

        int position = list_admin_complaints.getCheckedItemPosition();
        if (position==-1) {
            Toast.makeText(AdminWelcome.this, "Please select a complaint", Toast.LENGTH_SHORT).show();

        } else {
            String complaintID = lstComp.get(position);
            updateComplaintStatus(complaintID,2);
            String cookID = lstCook.get(position);
            updateCookStatus(cookID,2);
        }
    }
    public void OnSuspend( View view){
        int position = list_admin_complaints.getCheckedItemPosition();
        if (position==-1) {
            Toast.makeText(AdminWelcome.this, "Please select a complaint", Toast.LENGTH_SHORT).show();

        } else {
            String complaintID = lstComp.get(position);
            updateComplaintStatus(complaintID,3);
            String cookID = lstCook.get(position);
            updateCookStatus(cookID,3);
        }
    }

    public void OnTerminate( View view){
        int position = list_admin_complaints.getCheckedItemPosition();
        if (position==-1) {
            Toast.makeText(AdminWelcome.this, "Please select a complaint", Toast.LENGTH_SHORT).show();

        } else {
            String complaintID = lstComp.get(position);
            updateComplaintStatus(complaintID,4);
            String cookID = lstCook.get(position);
            updateCookStatus(cookID,4);
        }   }
    public void updateCookStatus(String cookID, int cookStatus) {
        DocumentReference doc = db.collection("cook").document(cookID);
        doc
                .update("cookStatus", cookStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminWelcome.this, "Cook status update", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    public void updateComplaintStatus(String complaintID, int complaintStatus) {
        DocumentReference doc = db.collection("complaint").document(complaintID);
        doc
                .update("complaintStatus", complaintStatus)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AdminWelcome.this, "Complaint status update", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
    public void OnIncludePast( View view){
        if (IncludePast==1)
        {
            IncludePast=4;
        }
        else
        {
            IncludePast=1;
        }
        displayComplaints();
    }

}
