package kit.edu.mensameet.server.model;

/**
 * This enum represents the genders which are part of the user profile
 *
 */
public enum Gender {
	MALE("Männlich"), FEMALE("Weiblich"), OTHER("Divers");

	private String name;

	/**
	 * 
	 * @param name is the name of each gender
	 */
	private Gender(String name) {
		this.name = name;
	}

	/**
	 * this method returns the name of a gender
	 */
	public String toString() {
		return name;
	}
}