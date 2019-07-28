package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Date;
/*
 * This class represents a user which is part of the server Model
 */
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
	private boolean isAdmin = false;
	private String groupToken;
	/*
	 * This constructor only contains the unique token, because a user first register himself with an e-mail and
	 * password which already creates a user. The necessary profile parameters are added later
	 */
	public User(String token) {
		this.token = token;
	}
	
	public User() {}
	
	public User(String token, 
				String name, 
				String motto, 
				Date birthdate, 
				Gender gender, 
				Subject subject, 
				Status status, 
				int profilePictureId,
				boolean isAdmin) {
		this.token = token;
		this.name = name;
		this.motto = motto;
		this.birthdate = birthdate;
		this.gender = gender;
		this.subject = subject;
		this.status = status;
		this.profilePictureId = profilePictureId;
		this.isAdmin = isAdmin;
	}
	
	/*
	 * default getter for the name
	 */
	public String getName() {
		return name;
	}
	/*
	 * default setter for the name
	 */
	public void setname(String name) {
		this.name = name;
	}
	/*
	 * default getter for the token
	 */
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	/*
	 * default getter for the motto
	 */
	public String getMotto() {
		return motto;
	}
	/*
	 * default setter for the motto
	 */
	public void setMotto(String motto) {
		this.motto = motto;
	}
	/*
	 * default getter for the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}
	/*
	 * default setter for the birthdate 
	 */
	public void setDate(Date birthdate) {
		this.birthdate = birthdate;
	}
	/*
	 * default getter for the gender
	 */
	public Gender getGender() {
		return gender;
	}
	/*
	 * default setter for the gender 
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	/*
	 * default getter for the subject
	 */
	public Subject getSubject() {
		return subject;
	}
	/*
	 * default setter for the subject
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	/*
	 * default getter for the status
	 */
	public Status getStatus() {
		return status;
	}
	/*
	 * default setter for the status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/*
	 * default getter for the profile picture id
	 */
	public int getProfilePictureId() {
		return profilePictureId;
	}
	/*
	 * default setter for the profile picture id
	 */
	public void setProfilePictureId(int profilePictureId) {
		this.profilePictureId = profilePictureId;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public String getGroupToken() {
		return groupToken;
	}
	
	public void setGroupToken(String groupToken) {
		this.groupToken = groupToken;
	}
}