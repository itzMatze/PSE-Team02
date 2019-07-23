package kit.edu.mensameet.server.model;
/*
 * This enum represents the given meal Lines
 */
public enum MealLine {
	LINE_ONE("Linie 1 "),
	LINE_TWO("Linie 2 "),
	LINE_THREE("Linie 3 "),
	LINE_FOUR_FIVE("Linie 4/5 "),
	CUTLET_BAR("Schnitzelbar "),
	LINE_SIX("L6 Update "),
	LATE_LINE("Spätausgabe und Abendessen "),
	CURRY_LINE("[kœri]werk "),
	CAFETARIA("Cafeteria Heiße Theke "),
	CAFETARIA_LATE("Cafeteria ab 14:30 "),
	PIZZA_LINE("[pizza]werk Pizza "),
	PIZZA_LINE_PASTA("[pizza]werk Pasta "),
	PIZZA_LINE_SALAD("[pizza]werk Salate / Vorspeisen ");
	
	
	
private String name;
	/*
	 * With this constructor we can add a String containing the name, in particular the german translation
	 */
	private MealLine(String name) {
		this.name = name;
	}
	/*
	 * This method returns the name of the line
	 */
	public String toString(){
        return name;
    }
}