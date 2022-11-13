package com.example.projetfinale;

public class Cook extends User{
    private String address;
    public  Cook(String firstName, String lastName, String emailAddress, String password, String address){
        super(firstName,lastName,emailAddress,password);
        this.address = address;
    }

    public String getAddress(){
        return address;
    }
}
