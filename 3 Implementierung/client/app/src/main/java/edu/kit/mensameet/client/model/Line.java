package edu.kit.mensameet.client.model;

/**
 * Representation of a mensa line containing several meals.
 */
public class Line {

    private String mealLine;

    private String[] meals;

    public Line() {}
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

        return this.mealLine.equals(that.mealLine);
    }

    public String getMealLine() {
        return mealLine;
    }

    public void setMealLine(String mealLine) {
        this.mealLine = mealLine;
    }

    public String[] getMeals() {
        return meals;
    }

    public void setMeals(String[] meals) {
        this.meals = meals;
    }

}
