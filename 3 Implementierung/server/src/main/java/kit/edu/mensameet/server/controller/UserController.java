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
		User user = repository.getUserByToken(token);
		return user;
	}
		
	public void addUser(String token) {
		repository.save(new User(token));
	}
	
	public boolean removeUser(String token) {
		User user = repository.getUserByToken(token);
		if(user == null) {
			return false;
		}
		else {
			repository.delete(user);
			return true;
		}
	}
	
	public boolean updateUser(User user) {
		User userOld = repository.getUserByToken(user.getToken());
		
		if (userOld == null) {
			return false;
		} 
		else {
			repository.delete(userOld);
			repository.save(user);
			return true;
		}
	}
	
	public User[] getAllUser() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).toArray(User[]::new);
	}

}
