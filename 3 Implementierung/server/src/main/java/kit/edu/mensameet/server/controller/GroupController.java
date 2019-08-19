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
	
	/**
	 * Returns a group identified by the token.
	 * 
	 * @param token of the group.
	 * @return the group identified by the token.
	 */
	public Group getGroup(String token) {
		Group group = groupRepository.getGroupByToken(token);
		
		if (group == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with token " + token + " coulnd't be found.");
		}
		
		return group;
	}
	
	/**
	 * Adds a group to the database.
	 * 
	 * @param group the group to be added.
	 * @return the just added group.
	 */
	public Group addGroup(Group group) {
		return groupRepository.save(group);
	}
	
	/**
	 * Removes a group from the database.
	 * 
	 * @param groupToken the token of the group that should be deleted.
	 */
	public void removeGroup(String groupToken) {
		Group group = groupRepository.getGroupByToken(groupToken);
		
		if(group != null) {
			group.getMembers().forEach(member -> {
				member.setGroupToken(null);
				userRepository.save(member);
			});
			
			groupRepository.delete(group);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Group with token " + groupToken + " coulnd't be found.");
		}
	}
	
	/**
	 * Removes all groups from the repository.
	 */
	public void removeAllGroups() {
		groupRepository.deleteAll();
		userRepository.findAll().forEach(member -> {
			member.setGroupToken(null);
		});
	}
	
	/**
	 * Returns all groups. Is only supposed to be used for testing purposes.
	 * @return all groups in the database.
	 */
	public Group[] getAllGroups() {
		Group[] groups = StreamSupport.stream(groupRepository.findAll().spliterator(), false).toArray(Group[]::new);
		return groups;
	}
	
	/**
	 * Returns all groups fitting to the preferences.
	 * 
	 * @param pref the preferences that should be searched with.
	 * @return all groups fitting to the preferences.
	 */
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