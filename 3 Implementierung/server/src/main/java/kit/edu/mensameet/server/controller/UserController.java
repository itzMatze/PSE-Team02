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

import kit.edu.mensameet.server.model.User;

@Component
public class UserController {
	
	@Value("${admin.token}")
	private String adminToken;

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private FirebaseAuthentifcator fbAuth;
	
	public User getUser(String token) {
		String userToken = fbAuth.encryptToUserToken(token);
		User user = repository.getUserByToken(userToken);
		
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with token " + userToken + " coulnd't be found.");			
		}
		
		return user; 
	}
	
	public void addUserWithToken(String fbToken) {
		String userToken = fbAuth.encryptToUserToken(fbToken);
		
		repository.save(new User(userToken));
	}
	
	public void updateUser(User user, String userToken) {
		//used to check wether user exists, if not an exception is thrown.
		getUser(userToken);
		user.setToken(userToken);
		
		repository.save(user);
	}
	
	public void deleteUser(String userToken) {		
		User userToDelete = getUser(userToken);
		
		if (userToDelete == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with token " + userToken + " coulnd't be found.");
		} 

		repository.delete(userToDelete);
		
		try {
			FirebaseAuth.getInstance().deleteUser(userToken);
		} catch (FirebaseAuthException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with token " + userToken + " coulnd't be found in firebase database.");			
		}
		
	}
	
    
    public void initializeAdminUser() {
    	User adminUser = repository.getUserByToken(adminToken);
    	
    	if (adminUser == null) {
    		adminUser = new User(adminToken, null, null, null, null, null, null, 0, true);
    	}
    	
    	repository.save(adminUser);
    }
}
