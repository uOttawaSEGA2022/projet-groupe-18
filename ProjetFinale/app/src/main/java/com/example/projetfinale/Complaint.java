package com.example.projetfinale;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Complaint {
    private Cook Cook;
    private String Description;
    private String id;
    private Activity Explain;
    List<Complaint> complaints;

    public Complaint(String Description, Cook cook){
        this.Description = Description;
        this.Cook = cook;
    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getDescription(){
        return Description;
    }
    public void setDescription(String Description){
        this.Description = Description;
    }
    public String getCookName(){
        return Cook.getFirstName();
    }
    public Cook getCook(){
        return Cook;
    }
    public void setCook(Cook cook){
        Cook = cook;
    }
    public Complaint(Activity Explain, List<Complaint> complaints){
        //super(Explain, R.layout.activity_client_complaints, complaints);
        this.Explain = Explain;
        this.complaints = complaints;
    }

    //@Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = Explain.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_client_complaints, null, true);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.tctComplaint);
        Complaint complaint = complaints.get(position);
        textViewDescription.setText(complaint.getDescription());
        return listViewItem;
    }
}
