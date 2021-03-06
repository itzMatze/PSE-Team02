package kit.edu.mensameet.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kit.edu.mensameet.server.controller.FirebaseAuthentifcator;
import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.controller.MembershipController;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.User;

@RestController
public class MembershipService {
	@Autowired
	private UserController userController;

	@Autowired
	private GroupController groupController;
	
	@Autowired
	private MembershipController membershipController;
	
	@Autowired
	private FirebaseAuthentifcator fbAuthentificator;
	
	/**
	 * Adds a user to a group.
	 * 
	 * @param firebaseToken for verifying the request and to be encrypted to the user token.
	 * @param groupToken of the group, that the user should get added to.
	 */
	@PostMapping("add-user-to-group")
	public void addUserToGroup(@RequestParam(value = "firebaseToken") String firebaseToken,
							   @RequestParam(value = "groupToken") String groupToken) {
		String userToken = fbAuthentificator.authenticateAndEncryptFirebaseTokenToUserToken(firebaseToken);
		User user = userController.getUser(userToken);
		Group group = groupController.getGroup(groupToken);
		
		membershipController.addUserToGroup(user, group);
	}

	/**
	 * Removes a user from a group.
	 * 
	 * @param firebaseToken for verifying the request and to be encrypted to the user token.
	 * @param groupToken groupToken of the group, that the user should get removed from.
	 */
	@PostMapping("remove-user-from-group")
	public void removeUserFromGroup(@RequestParam(value = "firebaseToken") String firebaseToken,
								    @RequestParam(value = "groupToken") String groupToken) {
		String userToken = fbAuthentificator.authenticateAndEncryptFirebaseTokenToUserToken(firebaseToken);
		User user = userController.getUser(userToken);
		Group group = groupController.getGroup(groupToken);
		
		membershipController.removeUserFromGroup(user, group);
	}
}
