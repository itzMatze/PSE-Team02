package kit.edu.mensameet.server.model;

public enum Status {
	PROFESSOR("Professor"),
	COLLEGE_STUDENT("Student"),
	STUDENT("Schüler"),
	APPRENTICE("Azubi"),
	OTHER("Andere");
	
	private String name;
	
	private Status(String name) {
		this.name = name;
	}
	
	public String toString(){
        return name;
    }

}
