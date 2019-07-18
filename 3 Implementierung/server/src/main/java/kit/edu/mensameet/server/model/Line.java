package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * This class represents a mensa line, containing the meals and the meal Line name
 */
@Entity
@Table (name = "mm_lines")
public class Line {
	@Id
	@GeneratedValue
	private int id;
	private Meal[] meals;
	private MealLine mealLine;
	
	public Line() {}
	
	public Line(MealLine mealLine, Meal[] meals) {
		this.mealLine = mealLine;
		this.meals = meals;
	}
	
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