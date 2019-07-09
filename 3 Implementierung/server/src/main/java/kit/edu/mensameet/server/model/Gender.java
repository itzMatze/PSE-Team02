package kit.edu.mensameet.server.model;

public enum Gender {
	MALE("Männlich"),
	FEMALE("Weiblich"),
	OTHER("Divers");
	
	private String name;
	
	private Gender(String name) {
		this.name = name;
	}
	
	public String toString(){
        return name;
    }

}
