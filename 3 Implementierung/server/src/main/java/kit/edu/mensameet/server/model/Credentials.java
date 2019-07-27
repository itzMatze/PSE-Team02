package kit.edu.mensameet.server.model;

/**
 * This class represents the credentials which are needed to log into the app
 * 
 * @author leo
 *
 */
public class Credentials {
	private String email;
	private String password;

	/**
	 * 
	 * @return the email of an user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @return the password of an user
	 */
	public String getPassword() {
		return password;
	}

	/*
	 * Constructor for Credentials
	 * 
	 * @Param email - String representing the E-Mail-address of the client
	 * 
	 * @Param password - String representing the chosen password of the client
	 * 
	 * @author Kathrin Blum
	 */
	public Credentials(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
