package com.example.projetfinale;

public class Client extends User{
    private String address;
    public Client(String firstName, String lastName, String emailAddress, String password, String address){
        super(firstName,lastName,emailAddress,password);
        this.address = address;
    }

    public String getAdress(){
        return address;
    }
}
