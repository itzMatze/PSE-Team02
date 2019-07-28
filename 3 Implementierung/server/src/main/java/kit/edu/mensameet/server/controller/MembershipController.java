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
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;
	
	/**
	 * Adss a user to a group.
	 * 
	 * @param user the user to be added.
	 * @param group the group the user should get added to.
	 */
	public void addUserToGroup(User user, Group group) {
		if (group.getMembers().size() + 1 > group.getMaxMembers()) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Group is already full.");
		} else {
			group.addMembers(user);
			groupRepository.save(group);
			user.setGroupToken(group.getToken());
			userRepository.save(user);
		}
	}
	
	/**
	 * Removes a user from a group.
	 * 
	 * @param user the user that should get removed.
	 * @param group the group the user should get removed from.
	 */
	public void removeUserFromGroup(User user, Group group) {
		Iterator<User> iterator = group.getMembers().iterator();
		
		while (iterator.hasNext()) {
			User iteratedUser = iterator.next();
			
			if (iteratedUser.getToken() == user.getToken()) {
				group.getMembers().remove(iteratedUser);
				
				if (group.getMembers().size() == 0) {
					groupRepository.save(group);
					groupRepository.delete(group);
				} else {
					groupRepository.save(group);					
				}
				
				user.setGroupToken(null);
				userRepository.save(user);
				
				return;
			}
		}
		
		throw new ResponseStatusException(HttpStatus.CONFLICT, "User with token " + user.getToken() + " couldn't be found in group.");
	}
}
