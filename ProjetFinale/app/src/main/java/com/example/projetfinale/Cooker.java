package com.example.projetfinale;

public class Cooker extends User{
    private String address;
    public  Cooker(String firstName, String lastName, String emailAddress, String password, String address){
        super(firstName,lastName,emailAddress,password);
        this.address = address;
    }

    public String getAddress(){
        return address;
    }
}
