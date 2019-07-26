package kit.edu.mensameet.server.controller;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.User;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
public class MembershipController {
	
	@Autowired
	private GroupRepository groupRepository;
	
	public void addUserToGroup(User user, Group group) {
		if (group.getMemberCount() + 1 > group.getMaxMembers()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Group is already full.");
		} else {
			group.addMembers(user);
			groupRepository.save(group);
		}
	}
	
	public void removeUserFromGroup(User user, Group group) {
		Iterator<User> iterator = group.getMembers().iterator();
		
		while (iterator.hasNext()) {
			User iteratedUser = iterator.next();
			
			if (iteratedUser.getToken() == user.getToken()) {
				group.getMembers().remove(iteratedUser);
				
				if (group.getMemberCount() == 0) {
					groupRepository.delete(group);
				} else {
					groupRepository.save(group);					
				}
				
				return;
			}
		}
		
		throw new ResponseStatusException(HttpStatus.CONFLICT, "User with token " + user.getToken() + " couldn't be found in group.");
	}
}
