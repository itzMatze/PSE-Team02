package server.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import kit.edu.mensameet.server.controller.UserRepository;
import kit.edu.mensameet.server.view.UserService;

public class UserControllerTest {

	@Autowired
	UserRepository repository;
	
	@Autowired
	UserService service;
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
