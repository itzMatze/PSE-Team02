package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.Clock;
/*
 * This class represents a group, which is part of the server Model
 */
@Entity
@Table (name = "groups")
public class Group {
	@Id
	private String token;
	private String name;
	private String motto;
	private int maxMembers;
	private Line line;
	//private  meetingTime;
	private User[] members;
	private int currentMembers;
	/*
	 * This constructor overrides the default constructor, which is neccessary for creating an array of users
	 * with the size of int maxMembers
	 */
	public Group(String token, String name, String motto,int maxMembers, Line line) {
		this.members = new User[maxMembers];
		this.token = token;
		this.name = name;
		this.motto = motto;
		this.maxMembers = maxMembers;
		this.line = line;
		
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
	
//	public Date getMeetingTime() {
//		return meetingTime;
//	}
//	
//	public void setMeetingTime(Date meetingTime) {
//		this.meetingTime = meetingTime;
//	}
}