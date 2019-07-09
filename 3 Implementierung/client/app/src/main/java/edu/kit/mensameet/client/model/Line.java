package edu.kit.mensameet.client.model;

public class Line {
    private Meal[] meals;

    public Line(Meal[] meals) {
        this.meals = meals;
    }

    public Meal[] getMeals() {
        return meals;
    }
}
