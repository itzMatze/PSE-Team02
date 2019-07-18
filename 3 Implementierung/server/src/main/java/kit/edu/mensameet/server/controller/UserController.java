package kit.edu.mensameet.server.controller;

import java.util.stream.StreamSupport;

import org.assertj.core.internal.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import kit.edu.mensameet.server.model.User;

@Component
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	public User getUser(String token) {
		return repository.getUserByToken(token);
	}
	
	public void addUserWithToken(String token) {
		repository.save(new User(token));
	}
	
	public boolean updateUser(User newUser) {
		User userToUpdate = getUser(newUser.getToken());
		
		if (userToUpdate != null) {
			userToUpdate = newUser;
			return true;
		}
		
		return false;
	}
	
	public void deleteUser(String token) {
		repository.delete(repository.getUserByToken(token));
	}
	
	public User[] getAllUser() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).toArray(User[]::new);
	}
}
