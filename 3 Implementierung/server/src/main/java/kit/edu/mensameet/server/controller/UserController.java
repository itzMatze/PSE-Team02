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
	
	public User[] getAllUser() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).toArray(User[]::new);
	}
	
	public void addUser(String name) {
		repository.save(new User(name));
	}
}
