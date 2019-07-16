package kit.edu.mensameet.server.model;
/*
 * This enum represents the genders which are part of the user profile
 */
public enum Gender {
	MALE("Männlich"),
	FEMALE("Weiblich"),
	OTHER("Divers");
	
	private String name;
	/*
	 * With the constructor we can give our enums a String containing their name, in particular their german translation
	 */
	private Gender(String name) {
		this.name = name;
	}
	/*
	 * This method returns the name of an enum
	 */
	public String toString(){
        return name;
    }
}