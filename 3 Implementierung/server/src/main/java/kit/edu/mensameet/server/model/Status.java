package kit.edu.mensameet.server.model;
/*
 * This enum represents the different status an user can choose
 */
public enum Status {
	PROFESSOR("Professor"),
	COLLEGE_STUDENT("Student"),
	STUDENT("Schüler"),
	APPRENTICE("Azubi"),
	OTHER("Andere");
	
	private String name;
	/*
	 * With this constructor we can add a String containg the name of a status, in particular the german translation
	 */
	private Status(String name) {
		this.name = name;
	}
	/*
	 * This method returns the name of a status
	 */
	public String toString(){
        return name;
    }
}