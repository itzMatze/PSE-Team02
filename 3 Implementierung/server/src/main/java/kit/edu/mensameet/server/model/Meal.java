package kit.edu.mensameet.server.model;

/**
 * This class represents a Meal, which contains a name, a price, the ingredients
 * and a foodtype
 *
 */
public class Meal {

	private String name;
	private float price;
	private Ingredient[] ingredients;
	private FoodType[] foodtypes;

	/**
	 * 
	 * @return the name of this meal
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name is the name of this meal
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the price of this meal
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * 
	 * @param price is the price of a meal as a float
	 */
	public void setprice(float price) {
		this.price = price;
	}

	/**
	 * 
	 * @return an array of all ingredients
	 */
	public Ingredient[] getIngredients() {
		return ingredients;
	}

	/**
	 * 
	 * @param ingredients are all ingredients which are in this meal
	 */
	public void setIngredients(Ingredient[] ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * 
	 * @return an array of foodtypes
	 */
	public FoodType[] getFoodType() {
		return foodtypes;
	}

	/**
	 * 
	 * @param foodtypes are the foodtypes of this meal
	 */
	public void setFoodType(FoodType[] foodtypes) {
		this.foodtypes = foodtypes;
	}
}