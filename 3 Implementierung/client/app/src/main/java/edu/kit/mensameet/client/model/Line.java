package edu.kit.mensameet.client.model;

/**
 * Representation of a mensa line containing several meals.
 */
public class Line {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMeals(Meal[] meals) {
        this.meals = meals;
    }

    private String name;

    private Meal[] meals;

    public Line(String name, Meal[] meals) {
        this.name = name;
        this.meals = meals;
    }

    public Meal[] getMeals() {
        return meals;
    }

    /**
     * Equality comparison by name.
     *
     * @param other Another line.
     * @return Equality.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Line)) {
            return false;
        }

        Line that = (Line) other;

        return this.name.equals(that.name);
    }
}
