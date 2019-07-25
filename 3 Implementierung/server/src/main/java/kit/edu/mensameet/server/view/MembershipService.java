package kit.edu.mensameet.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.controller.MembershipController;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.User;

@RestController
public class MembershipService {
	@Autowired
	UserController userController;

	@Autowired
	GroupController groupController;
	
	@Autowired
	MembershipController membershipController;
	
	@PostMapping("addUserToGroup")
	public void addUserToGroup(@RequestHeader(value = "token") String token, 
							   @RequestParam(value = "userToken") String userToken, 
							   @RequestParam(value = "groupToken") String groupToken) {
		User user = userController.getUser(userToken);
		Group group = groupController.getGroup(groupToken);
		
		membershipController.addUserToGroup(user, group);
		
	}
}
