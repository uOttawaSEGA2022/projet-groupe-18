package com.example.projetfinale;

public class Menu {
    Meal[] meals = new Meal[30];
    int iterator = 0;
    public void addMeal(Meal meal){
        meals[iterator] = meal;
        iterator++;
    }

    public void deleteMeal(Meal meal){
        for(int i = 0; i<meals.length; i++) {
            if (meals[i] == meal) {
                meals[i] = null;
            }
        }
    }

    public void setAvailability(Meal meal, boolean status){


    }
}
