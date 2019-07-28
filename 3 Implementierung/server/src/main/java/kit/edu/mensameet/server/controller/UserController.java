package kit.edu.mensameet.server.controller;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.User;

@Component
public class UserController {
	
	@Value("${admin.token}")
	private String adminToken;

	@Value("${production.mode}")
	private boolean productionMode;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private MembershipController membershipController;
	
	@Autowired
	private FirebaseAuthentifcator fbAuth;
	
	public User getUser(String userToken) {
		User user = userRepository.getUserByToken(userToken);
		
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with token " + userToken + " coulnd't be found.");			
		}
		
		return user; 
	}
	
	public void addUserWithToken(String userToken) {
		User user = userRepository.getUserByToken(userToken);
		
		if (user != null) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User with token " + userToken + " already exists.");
		}
		
		userRepository.save(new User(userToken));
		
		
		
	}
	
	public void updateUser(User user, String userToken) {
		//used to check wether user exists, if not an exception is thrown.
		getUser(userToken);
		user.setToken(userToken);
		
		userRepository.save(user);
	}
	
	public void deleteUser(String userToken) {		
		User userToDelete = getUser(userToken);
		
		if (userToDelete == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with token " + userToken + " coulnd't be found.");
		} 
		
		//remove user from group to avoid hibernate, mysql conflicts.
		if (userToDelete.getGroupToken() != null) {			
			membershipController.removeUserFromGroup(userToDelete, groupRepository.getGroupByToken(userToDelete.getGroupToken()));
		}
		
		userRepository.delete(userToDelete);
		
		if (productionMode) {			
			try {
				FirebaseAuth.getInstance().deleteUser(userToken);
			} catch (FirebaseAuthException e) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with token " + userToken + " coulnd't be found in firebase database.");			
			}
		}
	}
	
    
    public void initializeAdminUser() {
    	User adminUser = userRepository.getUserByToken(adminToken);
    	
    	if (adminUser == null) {
    		adminUser = new User(adminToken, null, null, null, null, null, null, 0, true);
    		userRepository.save(adminUser);
    	}
    	
    	System.out.println("admin user was added.");
    }
}
