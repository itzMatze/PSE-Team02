package kit.edu.mensameet.server.controller;

import org.springframework.data.repository.CrudRepository;

import kit.edu.mensameet.server.model.User;

public interface UserRepository extends CrudRepository<User, String>{
	User getUserByToken(String token);	
}
