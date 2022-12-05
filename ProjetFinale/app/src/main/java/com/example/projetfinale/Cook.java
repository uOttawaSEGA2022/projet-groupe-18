package com.example.projetfinale;

public class Cook extends User{
    private String address;
    private Boolean suspended = false; // this would make it so that if it is true then the cook will not have the ability to add to the menu
    public  Cook(String firstName, String lastName, String emailAddress, String password, String address){
        super(firstName,lastName,emailAddress,password);
        this.address = address;
    }

    public void setSuspend(){
        suspended = true;
    }

    public boolean getSuspensionStatus(){
        return suspended;
    }

    public String getAddress(){
        return address;
    }

}
