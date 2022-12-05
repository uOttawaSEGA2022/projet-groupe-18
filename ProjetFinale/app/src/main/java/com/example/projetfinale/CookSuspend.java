package com.example.projetfinale;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CookSuspend extends AppCompatActivity {
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
        returnToComplaint.putExtra("Suspension length", length);
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
}
