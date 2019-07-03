package kit.edu.mensameet.server.controller;

import java.util.stream.StreamSupport;

import org.assertj.core.internal.Iterables;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import kit.edu.mensameet.server.model.User;

@Component
public class UserController {
	
	private UserRepository repository;
	
	public User[] getAllUser() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).toArray(User[]::new);
	}
}
