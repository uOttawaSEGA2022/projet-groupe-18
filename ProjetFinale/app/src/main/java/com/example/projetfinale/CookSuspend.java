package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CookSuspend extends AppCompatActivity {
    ListView ComplaintList;
    List<Complaint> complaints;
    DatabaseReference databaseComplaints;
    private String id;
    Button SuspendButton;
    EditText Date;
    EditText Name;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        Date = (EditText) findViewById(R.id.Date);
        Intent currentIntent = getIntent();
        id = currentIntent.getStringExtra("ID");
        SuspendButton = findViewById(R.id.SuspendButton);
        complaints = new ArrayList<>();
        ComplaintList = findViewById(R.id.ComplaintList);
        databaseComplaints = FirebaseDatabase.getInstance().getReference("Complaints");
        SuspendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String date = Date.getText().toString();
                if(!TextUtils.isEmpty(date)){
                    if(verify(date)){
                        suspend(date);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Date invalide", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Entrer une date", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void suspend(String length){
        Intent returnToComplaint = new Intent(getApplicationContext(), Complaint.class);
        returnToComplaint.putExtra("ID", id);
        returnToComplaint.putExtra("Longeur de suspension", length);
        setResult(RESULT_OK, returnToComplaint);
        finish();
    }
    private Boolean verify(String date){
        Boolean isValid = false;
        int d, m, y;
        if(date.length()<10){
            return isValid;
        }
        try{
            d = Integer.parseInt(date.substring(0, 2));
            m = Integer.parseInt(date.substring(3, 5));
            y = Integer.parseInt(date.substring(6));
        }
        catch(Exception e){
            return isValid;
        }
        if(m < 13 && m > 0){
            if(d < 32 && d > 0){
                if(y > 2021){
                    isValid = true;
                }
            }
        }
        return isValid;
    }
    protected void onStart() {
        super.onStart();
        databaseComplaints.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaints.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Complaint complaint = postSnapshot.getValue(Complaint.class);
                    complaints.add(complaint);
                }
                Complaint complaintsAdapter = new Complaint(CookSuspend.this, complaints);
                ComplaintList.setAdapter((ListAdapter) complaintsAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String complaintID = data.getStringExtra("Id");
        String length = data.getStringExtra("Durée de suspension");
        suspendCook(complaintID, length);
    }
    private void suspendCook(String id, String suspensionDate) {
        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
        databaseComplaints.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot dataSnapshot = task.getResult();
                Complaint complaint = dataSnapshot.getValue(Complaint.class);
                if (complaint != null){
                    String cookID = complaint.getCookName();
                    DatabaseReference databaseChef = FirebaseDatabase.getInstance().getReference("Users").child(cookID);
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("banni", true);
                    result.put("Date de suspension", suspensionDate);
                    databaseChef.updateChildren(result);
                }
            }
        });
    }
}
