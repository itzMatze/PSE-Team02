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
		return repository.getUserByToken(userToken);
	}
	
	public void addUserWithToken(String fbToken) {
		String userToken = fbAuth.encryptToUserToken(fbToken);
		repository.save(new User(userToken));
	}
	
	public void updateUser(User newUser) {
		User userToUpdate = getUser(newUser.getToken());
		
		if (userToUpdate != null) {
			//Update the User and 
			repository.save(newUser);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with token " + newUser.getToken() + " coulnd't be found.");
		}
	}
	
	public void deleteUser(String token) {
		repository.delete(repository.getUserByToken(token));
	}
	
	public User[] getAllUser() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).toArray(User[]::new);
	}
}
