package kit.edu.mensameet.server.model;

/**
 * This enum represents to different food types which are inside the food
 * 
 *
 */
public enum FoodType {
	BEEF("Rindfleisch", "R"), BEEF_REGIONAL("regionales Rindfleisch aus artgerechter Tierhaltung", "RAT"),
	PORK("Schweinefleisch", "S"), PORK_REGIONAL("regionales Schweinefleisch aus artgerechter Tierhaltung", "SAT"),
	FISH("Fisch", "MSC"), VEGETARIAN("Vegetarisch", "VEG"), VEGAN("Vegan", "VG"), LAB("Lab", "LAB"),
	ORGANIC("Bio", "B"), MENSA_VITAL("Mensa Vital", "MV");

	private String name;
	private String id;

	/**
	 * 
	 * @param name is the name of each food type
	 * @param id   is the id of each food type
	 */
	private FoodType(String name, String id) {
		this.name = name;
		this.id = id;
	}

	/**
	 * This method returns the name of each food type
	 */
	public String toString() {
		return name;
	}

	/**
	 * 
	 * @return the ID of each food type
	 */
	public String getID() {
		return id;
	}
}