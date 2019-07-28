package server.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import kit.edu.mensameet.server.Application;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.controller.UserRepository;
import kit.edu.mensameet.server.model.User;
import kit.edu.mensameet.server.view.UserService;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc

/*
 * We need to mock Firebase Authentication somehow to make this work
 */

public class UserControllerTest {

	@Autowired
	UserRepository repository;
	
	@Autowired
	UserController controller;
	
	private User Anna = new User("token1"); 
	private User Ben = new User("token2");
	
	@Test
	public void shouldGetUserCorrectly() {
		controller.addUserWithToken("token1");
		assertEquals(controller.getUser("token1").getToken(), Anna.getToken());
		
	}
	
	@Test(expected = ResponseStatusException.class)
	public void shouldFailGettingUser() {
		controller.addUserWithToken("token1");
		controller.getUser("token2");
	}

	@After
	public void tearDown() {
		repository.deleteAll();
	}
}
