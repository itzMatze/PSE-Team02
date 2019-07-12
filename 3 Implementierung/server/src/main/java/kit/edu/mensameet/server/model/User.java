package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name="users")
public class User {
	
	@Id
	private String token;
	private String name;
	private String motto;
	private Date birthdate;
	private Gender gender;
	private Subject subject;
	private Status status;
	private int profilePictureId;
	
	/* User registriert sich mit seiner E-Mail und einem Passwort und Firebase generiert einen Token 
	 * der zum Anlegen eines User Profils ausreicht
	 */
	public User(String token) {
		this.token = token;
	}

	public User() {}
	
	public String getName() {
		return name;
	}
	
	public void setname(String name) {
		this.name = name;
	}

	public String getToken() {
		return token;
	}
	
	public String getMotto() {
		return motto;
	}
	
	public void setMotto(String motto) {
		this.motto = motto;
	}
	
	public Date getBirthdate() {
		return birthdate;
	}
	
	public void setDate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public Subject getSubject() {
		return subject;
	}
	
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public int getProfilePictureId() {
		return profilePictureId;
	}
	
	public void setProfilePictureId(int profilePictureId) {
		this.profilePictureId = profilePictureId;
	}
	
	@Override
	public String toString() {
		return "{name: " + name + ", token: " + token + "}";
	}
}
