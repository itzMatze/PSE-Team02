package kit.edu.mensameet.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;

import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.MealLine;
import kit.edu.mensameet.server.model.Preference;
import kit.edu.mensameet.server.model.User;;


@Component
public class GroupController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private FirebaseAuthentifcator fbAuth;
	
	public Group getGroup(String token) {
		Group group = groupRepository.getGroupByToken(token);
		
		if (group == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with token " + token + " coulnd't be found.");
		}
		
		return group;
	}
	
	public Group addGroup(Group group) {
		return groupRepository.save(group);
	}
	
	public void removeGroup(String groupToken) {
		Group group = groupRepository.getGroupByToken(groupToken);
		
		if(group != null) {
			group.getMembers().forEach(member ->{
				member.setGroupToken(null);
				userRepository.save(member);
			});
			
			groupRepository.delete(group);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with token " + groupToken + " coulnd't be found.");
		}
	}
	
	public void removeAllGroups() {
		groupRepository.deleteAll();
		userRepository.findAll().forEach(member -> {
			member.setGroupToken(null);
		});
	}
	
	public Group[] getAllGroups() {
		Group[] groups = StreamSupport.stream(groupRepository.findAll().spliterator(), false).toArray(Group[]::new);
		return groups;
	}
	
	public Group[] getGroupByPreferences(Preference pref) {
		Group[] allGroups = getAllGroups();
		List<Group> fittingGroups = new ArrayList<Group>();
		
		for(Group group : allGroups) {
			if (pref.getStartTime().compareTo(group.getMeetingTime()) <= 0
			 && pref.getEndTime().compareTo(group.getMeetingTime()) >= 0) {
				for (MealLine line : pref.getMealLines()) {
					if (line == group.getLine()) {
						fittingGroups.add(group);						
					}
				}
			}
		}
		
		return fittingGroups.toArray(new Group[fittingGroups.size()]);
	}
}