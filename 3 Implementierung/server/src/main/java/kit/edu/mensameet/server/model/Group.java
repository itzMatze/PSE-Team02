package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import org.hibernate.annotations.CascadeType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.api.client.util.DateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

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
	private LocalTime meetingTime;
	
	@Enumerated(EnumType.STRING)
	private MealLine line;


	@OneToMany
	@OrderColumn
	@NotFound(action = NotFoundAction.IGNORE)
	private List<User> members;
	
	public Group() {
		this.token = UUID.randomUUID().toString();
	}

	public Group(String name, String motto, int maxMembers, MealLine line, LocalTime time) {
		this.name = name;
		this.motto = motto;
		this.maxMembers = maxMembers;
		this.line = line;
		this.meetingTime = time;
		this.token = UUID.randomUUID().toString();
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
	public MealLine getLine() {
		return line;
	}
	/*
	 * default setter for Line
	 */
	public void setMealLine(MealLine line) {
		this.line = line;
	}
	/*
	 * default getter for the group members
	 */
	public List<User> getMembers() {
		return members;
	}
	/*
	 * adds a user to the group and increments the current member count 
	 */
	public void addMembers(User user) {
		if (members.size() < maxMembers) {			
			members.add(user);
		}
	}
	
	public LocalTime getMeetingTime() {
		return meetingTime;
	}
	
	public void setMeetingTime(LocalTime meetingTime) {
		this.meetingTime = meetingTime;
		//LocalTime meetingTime = LocalTime.parse( "20:11:13" ); falls String
	}
}