package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.api.client.util.DateTime;
import java.time.LocalTime;

/*
 * This class represents a group, which is part of the server Model
 */

@Entity
@Table (name = "mm_groups")
public class Group {
	@Id
	private String token;
	private String name;
	private String motto;
	private int maxMembers;
	private int currentMembers;
	private LocalTime meetingTime;

	@OneToOne
	private Line line;
	
	private User[] members;
	/*
	 * This constructor overrides the default constructor, which is neccessary for creating an array of users
	 * with the size of int maxMembers
	 */
	public Group(String token, String name, String motto,int maxMembers, Line line, LocalTime meetingTime) {
		this.members = new User[maxMembers];
		this.token = token;
		this.name = name;
		this.motto = motto;
		this.maxMembers = maxMembers;
		this.line = line;
		this.meetingTime = meetingTime;
	}
	/*
	 * default setter for token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/*
	 * default getter for token
	 */
	public String getToken() {
		return token;
	}
	/*
	 * default getter for name
	 */
	public String getName() {
		return name;
	}
	/*
	 * default setter for name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/*
	 * default getter for motto
	 */
	public String getMotto() {
		return motto;
	}
	/*
	 * default setter for motto
	 */
	public void setMotto(String motto) {
		this.motto = motto;
	}
	/*
	 * default getter for the maximal member count
	 */
	public int getMaxMembers() {
		return maxMembers;
	}

	/*
	 * default getter for Line
	 */
	public Line getLine() {
		return line;
	}
	/*
	 * default setter for Line
	 */
	public void setLine(Line line) {
		this.line = line;
	}
	/*
	 * default getter for the group members
	 */
	public User[] getMembers() {
		return members;
	}
	/*
	 * adds a user to the group and increments the current member count 
	 */
	public boolean setMembers(User user) {
		if (currentMembers < maxMembers) {
			members[currentMembers] = user;
			currentMembers++;
			return true;
		}
		return false;
	}
	/*
	 * default getter for current member count
	 */
	public int getCurrentMembers() {
		return currentMembers;
	}
	/*
	 * default setter for current member count
	 */
	public void setCurrentMembers(int currentMembers) {
		this.currentMembers = currentMembers; 
	}
	
	public LocalTime getMeetingTime() {
		return meetingTime;
	}
	
	public void setMeetingTime(LocalTime meetingTime) {
		this.meetingTime = meetingTime;
		//LocalTime meetingTime = LocalTime.parse( "20:11:13" ); falls String
	}
}