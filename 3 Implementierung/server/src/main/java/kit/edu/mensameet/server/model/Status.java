package kit.edu.mensameet.server.model;
/**
 * This enum represents each status a user of the app can choose
 *
 */
public enum Status {
	PROFESSOR("Professor"),
	COLLEGE_STUDENT("Student"),
	STUDENT("Schüler"),
	APPRENTICE("Azubi"),
	OTHER("Andere");
	
	private String name;
	/**
	 * 
	 * @param name is the name of the status
	 */
	private Status(String name) {
		this.name = name;
	}
	/**
	 * return a status as a string
	 */
	public String toString(){
        return name;
    }
}