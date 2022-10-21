package com.example.projetfinale;

public class User {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

    public User(String firstName, String lastName, String emailAddress, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getEmailAddress(){
        return emailAddress;
    }
    public String getPassword(){
        return password;
    }
}
