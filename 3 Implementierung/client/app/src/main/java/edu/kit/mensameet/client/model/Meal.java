package edu.kit.mensameet.client.model;

public class Meal {
    private String name;
    private float price;
    private FoodType[] types;
    private Ingredients[] ingredients;

    public Meal(String name, float price, FoodType[] types) {

    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public FoodType[] getFoodTypes() {
        return types;
    }
}
