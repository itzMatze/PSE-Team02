package kit.edu.mensameet.server.controller;

import java.util.stream.StreamSupport;

import org.assertj.core.internal.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

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
	
	public void addGroup(Group group) {
		repository.save(group);
	}
	
	public boolean removeGroup(String groupToken) {
		Group group = repository.getGroupByToken(groupToken);
		
		if(group == null) {
			return false;
		} else {
			repository.delete(group);
			return true;
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
		Group[]fittingGroups = new Group[allGroups.length];
		
		int i = 0;
		int j = 0;
		
		while(i < allGroups.length) {
			MealLine[] prefLines = pref.getMealLines();
			
			for(int k = 0; k < prefLines.length; j++) {
				
				if(prefLines[k].name() == allGroups[i].getLine().getMealLine().name()) {
					
					
					
					
					
					
					//time match
					
					
					
					
					
					fittingGroups[j] = allGroups[i];
					j++;
					break;
				}
				else {
					k++;
				}
			}
			i++;
		}	
		return fittingGroups;
	}		
	
	
}