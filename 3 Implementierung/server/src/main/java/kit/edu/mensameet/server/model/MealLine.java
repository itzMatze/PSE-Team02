package kit.edu.mensameet.server.model;

public enum MealLine {
	LINE_ONE("Linie 1"),
	LINE_TWO("Linie 2"),
	LINE_THREE("Linie 3"),
	LINE_FOUR_FIVE("Linie 4/5"),
	CUTLET_BAR("Schnitzelbar"),
	LINE_SIX("L6 Update"),
	LATE_LINE("Spätausgabe und Abendessen"),
	CURRY_LINE("[kœri]werk"),
	CAFETARIA("Cafeteria Heiße Theke"),
	CAFETARIA_LATE("Cafeteria ab 14:30"),
	PIZZA_LINE("[pizza]werk - Pizza"),
	PIZZA_LINE_PASTA("[pizza]werk - Pasta"),
	PIZZA_LINE_SALAD("[pizza]werk - Salate/Vorspeisen");
	
	
	
private String name;
	
	private MealLine(String name) {
		this.name = name;
	}
	
	public String toString(){
        return name;
    }

}
