package kit.edu.mensameet.server.controller;

import java.util.stream.StreamSupport;

import org.assertj.core.internal.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import kit.edu.mensameet.server.model.User;

@Component
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private FirebaseAuthentifcator fbAuth;
	
	public User getUser(String token) {
		String userToken = fbAuth.encryptToUserToken(token);
		User user = repository.getUserByToken(userToken);
		
		if (user == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with token " + user.getToken() + " coulnd't be found.");			
		}
		
		return user; 
	}
	
	public void addUserWithToken(String fbToken) {
		String userToken = fbAuth.encryptToUserToken(fbToken);
		
		repository.save(new User(userToken));
	}
	
	public void updateUser(User user, String firebaseToken) {
		String userToken = fbAuth.encryptToUserToken(firebaseToken);
		
		//used to check wether user exists
		getUser(userToken);
		user.setToken(userToken);
		
		repository.save(user);
	}
	
	public void deleteUser(String firebaseToken) {
		String userToken = fbAuth.encryptToUserToken(firebaseToken);
		repository.delete(repository.getUserByToken(userToken));
	}
	
	public User[] getAllUser() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).toArray(User[]::new);
	}
}
