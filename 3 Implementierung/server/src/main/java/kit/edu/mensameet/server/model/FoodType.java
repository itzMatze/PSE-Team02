package kit.edu.mensameet.server.model;
/*
 * This enum represents to different food types which are inside the food
 */
public enum FoodType {
	BEEF("Rindfleisch"),
	REGIONAL_MEAT("regionales Rindfleisch aus artgerechter Tierhaltung"),
	PORK("Schweinefleisch"),
	REGIONAL_PORK("regionales Schweinefleisch aus artgerechter Tierhaltung"),
	FISH("Fisch"),
	VEGETARIAN("Vegetarisch"),
	VEGAN("Vegan");
	//LAB("Lab"),
	//ORGANIC(""),
	//MENSA_VITAL("");
	
private String name;
	/*
	 * With this constructor we can give the enums a String containing the name, in particular its german translation
	 */
	private FoodType(String name) {
		this.name = name;
	}
	/*
	 * This method overrides the default toString method and returns the name of the food type
	 */
	public String toString(){
        return name;
    }
}