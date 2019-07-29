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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class represents a group.
 *
 */

@Entity
@Table(name = "mm_groups")
public class Group {
	@Id
	private String token;
	private String name;
	private String motto;
	private int maxMembers;
	private LocalTime meetingTime;

	@Enumerated(EnumType.STRING)
	private MealLine line;


	@OneToMany(fetch = FetchType.EAGER)
	@OrderColumn
	@NotFound(action = NotFoundAction.IGNORE)
	private List<User> members = new ArrayList<User>();

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

	/**
	 * 
	 * @param token is a unique identification for each group
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 
	 * @return the unique token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 
	 * @return the name of a group
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name is the name of group
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the motto of a group as a string
	 */
	public String getMotto() {
		return motto;
	}

	/**
	 * 
	 * @param motto is the motto of a group
	 */
	public void setMotto(String motto) {
		this.motto = motto;
	}

	/**
	 * 
	 * @return an integer of the maximal number of members allowed in this group
	 */
	public int getMaxMembers() {
		return maxMembers;
	}

	/**
	 * 
	 * @return the MealLine chosen in this group
	 */
	public MealLine getLine() {
		return line;
	}

	/**
	 * 
	 * @param line is the MealLine were the group will eat
	 */
	public void setMealLine(MealLine line) {
		this.line = line;
	}

	/**
	 * 
	 * @return a list of the users who joined the group
	 */
	public List<User> getMembers() {
		return members;
	}

	/**
	 * 
	 * @param user is a user who wants to join the group
	 */
	public void addMembers(User user) {
		if (members.size() < maxMembers) {
			members.add(user);
		}
	}

	/**
	 * 
	 * @return the time this group wants to eat
	 */
	public LocalTime getMeetingTime() {
		return meetingTime;
	}

	/**
	 * 
	 * @param meetingTime is the time this group wants to go to eat
	 */
	public void setMeetingTime(LocalTime meetingTime) {
		this.meetingTime = meetingTime;
		// LocalTime meetingTime = LocalTime.parse( "20:11:13" ); falls String
	}
}