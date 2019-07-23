package kit.edu.mensameet.server.model;
/*
 * This enum represents to different food types which are inside the food
 */
public enum FoodType {
	BEEF("Rindfleisch", "R"),
	BEEF_REGIONAL("regionales Rindfleisch aus artgerechter Tierhaltung", "RAT"),
	PORK("Schweinefleisch", "S"),
	PORK_REGIONAL("regionales Schweinefleisch aus artgerechter Tierhaltung", "SAT"),
	FISH("Fisch", "MSC"),
	VEGETARIAN("Vegetarisch", "VEG"),
	VEGAN("Vegan", "VG"),
	LAB("Lab", "LAB"),
	ORGANIC("Bio", "B"),
	MENSA_VITAL("Mensa Vital", "MV");
	
private String name;
private String id;
	/*
	 * With this constructor we can give the enums a String containing the name, in particular its german translation
	 */
	private FoodType(String name, String id) {
		this.name = name;
		this.id = id;
	}
	/*
	 * This method overrides the default toString method and returns the name of the food type
	 */
	public String toString(){
        return name;
    }
	
	public String getID() {
		return id;
	}
}