package kit.edu.mensameet.server.model;

public class Credentials {
	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	/*
	 * Constructor for Credentials
	 * @Param email - String representing the E-Mail-address of the client
	 * @Param password - String representing the chosen password of the client
	 * @author Kathrin Blum
	 */
	public Credentials(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
