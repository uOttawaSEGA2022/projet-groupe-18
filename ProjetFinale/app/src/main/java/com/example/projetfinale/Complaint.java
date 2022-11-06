package com.example.projetfinale;

public class Complaint {
    private String Description;
    private Status cooker;
    public Complaint(String desc, Status cook ){
        cooker = cook;
        Description = desc;
    }

    public Status getCookStatus(){
        return cooker;
    }

    public String getDescription(){
        return Description;
    }
}
