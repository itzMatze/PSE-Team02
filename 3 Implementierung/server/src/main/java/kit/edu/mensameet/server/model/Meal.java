package kit.edu.mensameet.server.model;
/*
 * This class represents Meal, which contains a name, a price, the ingredients and a foodtype
 */
public class Meal {

		private String name;
		private float price;
		private Ingredient[] ingredients;
		private FoodType[] foodtypes;
		/*
		 * default getter for name
		 */
		public String getName() {
			return name;
		}
		/*
		 * default setter for name
		 */
		public void setName(String name) {
			this.name = name;
		}
		/*
		 * default getter for price
		 */
		public float getPrice() {
			return price;
		}
		/*
		 * default setter for price
		 */
		public void setprice(float price) {
			this.price = price;
		}
		/*
		 * default getter for the ingredients
		 */
		public Ingredient[] getIngredients() {
			return ingredients;
		}
		/*
		 * default setter for the ingredients
		 */
		public void setIngredients(Ingredient[] ingredients) {
			this.ingredients = ingredients;
		}
		/*
		 * default getter for the foodtype
		 */
		public FoodType[] getFoodType() {
			return foodtypes;
		}
		/*
		 * default setter for the foodtype
		 */
		public void setFoodType(FoodType[] foodtypes) {
			this.foodtypes = foodtypes;
		}
}