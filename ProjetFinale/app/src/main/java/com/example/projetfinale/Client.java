package com.example.projetfinale;

public class Client extends User {

    // Instance variables

    private String firstName;
    private String lastname;
    private String email;
    private String password;
    private String address;

    // Instance methods

    // Constructor
    public Client(String firstName, String lastName, String email, String password, String address){
        super(firstName,lastName,email,password);
        this.address = address;
    }

    // Getters
    public String getAddress(){
        return address;
    }

    // Setters
    public void setAddress(String newAddress) { address = newAddress; }

}
