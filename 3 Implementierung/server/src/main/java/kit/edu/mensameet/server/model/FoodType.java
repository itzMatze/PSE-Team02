package kit.edu.mensameet.server.model;

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
	
	private FoodType(String name) {
		this.name = name;
	}
	
	public String toString(){
        return name;
    }

}
