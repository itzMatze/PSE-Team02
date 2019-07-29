package kit.edu.mensameet.server.view;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.google.firebase.auth.FirebaseAuthException;

import kit.edu.mensameet.server.controller.FirebaseAuthentifcator;
import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.MealLine;
import kit.edu.mensameet.server.model.Preference;

@RestController
public class GroupService {
	
	@Autowired
	private GroupController groupController;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private FirebaseAuthentifcator fbAuth;
	
	/**
	 * Returns the group identified by the groupToken.
	 * 
	 * @param firebaseToken for verifying the request.
	 * @param groupToken The token of the group
	 * @return the group identified by groupToken.
	 */
	@RequestMapping("/group") 
	public Group getGroupByToken(@RequestHeader(value="firebaseToken") String firebaseToken, 
								 @RequestParam(value="groupToken") String groupToken) {
		fbAuth.authenticateFirebaseToken(firebaseToken);
		return groupController.getGroup(groupToken);
	}
	
	/**
	 * returns all groups fitting to the given prefferences.
	 * 
	 * @param firebaseToken  for verifying the request.
	 * @param prefs the prefferences, that fitting groups are search with.
	 * @return all group fitting to the prefferences.
	 */
    @PostMapping("/group-prefferences")
    Group[] getGroupsByPrefferences(@RequestHeader(value="firebaseToken") String firebaseToken, 
    								@RequestBody Preference prefs) {
    	fbAuth.authenticateFirebaseToken(firebaseToken);	
    	return groupController.getGroupByPreferences(prefs);
    }
    
    /**
     * Creates a group in the database.
     * 
     * @param firebaseToken  for verifying the request.
     * @param group the group that should get created.
     * @return the group that just was created with a created token.
     */
    @PostMapping("/create-group") 
    public Group createGroup(@RequestHeader(value="firebaseToken") String firebaseToken, @RequestBody Group group) {
    	fbAuth.authenticateFirebaseToken(firebaseToken);
    	return groupController.addGroup(group);
    }
    
    /**
     * Deletes a group from the database.
     * 
     * @param firebaseToken  for verifying the request and to be encrypted to 
     * 		  the user token who's corosponding user needs admin priviliges.
     * @param groupToken the token of the gorup that should get deleted.
     */
    @DeleteMapping("/group")
    public void deleteGroup(@RequestHeader(value="firebaseToken") String firebaseToken, 
    						@RequestParam(value="groupToken") String groupToken) {
    	String userToken = fbAuth.authenticateAndEncryptFirebaseTokenToUserToken(firebaseToken);
    	
		if (!userController.getUser(userToken).getIsAdmin()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with token " + userToken + " has no permission to do this action."); 
		}
    	
    	groupController.removeGroup(groupToken);
    }
}
