package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;;

@Entity
public class User {
	
	@Id
	private String token;
	private String name;
	
	public User() {}
	
	public User(String name) {
		this.name = name;
		this.token = name;
	}

	public String getName() {
		return name;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	@Override
	public String toString() {
		return "{name: " + name + ", token: " + token + "}";
	}
}
