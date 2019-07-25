package kit.edu.mensameet.server.controller;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import kit.edu.mensameet.server.controller.GroupController;

@Component
public class MembershipController {
	
	public boolean addUserToGroup(User user, Group group) {
		if (group.getCurrentMembers() + 1 > group.getMaxMembers()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Group is already full.");
		}
		
		
		if (group.setMembers(user) == true) {
			return true;
		}

		return false;	
	}
	
	public boolean removeUserFromGroup(User user, Group group) {
		for (int i = 0; i < group.getMaxMembers(); i++) {
			if (group.getMembers()[i].getToken() == user.getToken()) {
				group.getMembers()[i] = group.getMembers()[group.getCurrentMembers()];
				group.getMembers()[group.getCurrentMembers()] = null;
				group.setCurrentMembers(group.getCurrentMembers() - 1);
				return true;
			}
		}
		return false;
	}
}
