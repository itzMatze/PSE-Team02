package kit.edu.mensameet.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table (name = "Groups")
public class Group {
	@Id
	private String token;
	private String name;
	private String motto;
	private int maxMembers;
	private String line;
	private Date meetingTime;
	
	public Group() {
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
	
	//public void setToken(String token) {
	//	this.token = token;
	//}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMotto() {
		return motto;
	}
	
	public void setMotto(String motto) {
		this.motto = motto;
	}
	
	public int getMaxMembers() {
		return maxMembers;
	}
	
	public void setMaxMembers(int maxMembers) {
		this.maxMembers = maxMembers;
	}
	
	public String getLine() {
		return line;
	}
	
	public void setLine(String line) {
		this.line = line;
	}
	
	public Date getMeetingTime() {
		return meetingTime;
	}
	
	public void setMeetingTime(Date meetingTime) {
		this.meetingTime = meetingTime;
	}
	

}
