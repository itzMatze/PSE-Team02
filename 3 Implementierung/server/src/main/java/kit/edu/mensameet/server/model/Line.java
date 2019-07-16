package kit.edu.mensameet.server.model;
/*
 * This class represents a mensa line, containing the meals and the meal Line name
 */
public class Line {
	private Meal[] meals;
	private MealLine mealLine;
	/*
	 * default getter for the meals
	 */
	public Meal[] getMeals() {
		return meals;
	}
	/*
	 * default setter for the meals
	 */
	public void setMeals(Meal[] meals) {
		this.meals = meals;
	}
	/*
	 * default getter for meal line
	 */
	public MealLine getMealLine() {
		return mealLine;
	}
	/*
	 * default setter for the meal line
	 */
	public void setMealLine(MealLine mealLine) {
		this.mealLine = mealLine;
	}
}