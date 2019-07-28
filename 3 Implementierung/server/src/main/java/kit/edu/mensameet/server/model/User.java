package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Date;

/**
 * This class represents a user of the app
 *
 */
@Entity
@Table(name = "users")
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

	public User() {
	}

	/**
	 * This constructor only contains the unique token, because a user first
	 * register himself with an e-mail and password which already creates a user.
	 * The necessary profile parameters are added later
	 * 
	 * @param token is the user token
	 */

	public User(String token) {
		this.token = token;
	}

	public User(String token, String name, String motto, Date birthdate, Gender gender, Subject subject, Status status,
			int profilePictureId, boolean isAdmin) {
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

	/**
	 * 
	 * @return the name of the user
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name is the name of a user as a string
	 */
	public void setname(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the unique token of an user
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 
	 * @param token is the unique token of a user as a string
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 
	 * @return the motto of an user
	 */
	public String getMotto() {
		return motto;
	}

	/**
	 * 
	 * @param motto is the motto of a user as a string
	 */
	public void setMotto(String motto) {
		this.motto = motto;
	}

	/**
	 * 
	 * @return the birthdate of a user
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * 
	 * @param birthdate is the birthdate of an user
	 */
	public void setDate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * 
	 * @return the gender of a user
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * 
	 * @param gender is the gender of a user from an enum class
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * 
	 * @return the subject of an user
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * 
	 * @param subject is the subject of a user with the status prof or college
	 *                student
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * 
	 * @return the status of a user
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status is the status of an user from the enum class status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * 
	 * @return the id of a profilepicture the user choosed
	 */
	public int getProfilePictureId() {
		return profilePictureId;
	}

	/**
	 * 
	 * @param profilePictureId is an integer of the id of a profile picture
	 */
	public void setProfilePictureId(int profilePictureId) {
		this.profilePictureId = profilePictureId;
	}

	/**
	 * 
	 * @return a boolean wether an user is an administrator or not
	 */
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