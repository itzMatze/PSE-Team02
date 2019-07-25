package kit.edu.mensameet.server.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.assertj.core.internal.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.MealLine;
import kit.edu.mensameet.server.model.Preference;
import kit.edu.mensameet.server.model.User;;


@Component
public class GroupController {
	
	@Autowired
	private GroupRepository repository;
	
	public Group getGroup(String token) {
		Group group = repository.getGroupByToken(token);
		return group;
	}
	
	public Group addGroup(Group group) {
		return repository.save(group);
	}
	
	public void removeGroup(String groupToken) {
		Group group = repository.getGroupByToken(groupToken);
		
		if(group != null) {
			repository.delete(group);
		}
	}
	
	public void removeAllGroups() {
		repository.deleteAll();
	}
	
	public Group[] getAllGroups() {
		Group[] groups = StreamSupport.stream(repository.findAll().spliterator(), false).toArray(Group[]::new);
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