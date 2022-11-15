package com.example.projetfinale;

public class User {

    // Instance variable

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Instance methods

    // Constructors
    public User(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName(){ return lastName; }
    public String getEmailAddress() { return email; }
    public String getPassword(){ return password; }

    // Setters
    public void setFirstName(String newFirstName) { firstName = newFirstName; }
    public void setLastName(String newLastName) { lastName = newLastName; }
    public void setEmail(String newEmail) { email = newEmail; }
    public void setPassword(String newPassword) { password = newPassword; }

}
