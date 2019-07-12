package kit.edu.mensameet.server.model;

public class Meal {

		private String name;
		private float price;
		private Ingredient[] ingredients;
		private FoodType[] foodtypes;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public float getPrice() {
			return price;
		}
		
		public void setprice(float price) {
			this.price = price;
		}
		
		public Ingredient[] getIngredients() {
			return ingredients;
		}
		
		public void setIngredients(Ingredient[] ingredients) {
			this.ingredients = ingredients;
		}
		
		public FoodType[] getFoodType() {
			return foodtypes;
		}
		
		public void setFoodType(FoodType[] foodtypes) {
			this.foodtypes = foodtypes;
		}
	

}
