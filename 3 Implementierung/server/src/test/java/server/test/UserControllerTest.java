package server.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${admin.token}")
	private String adminToken;
	
	@Autowired
	UserRepository repository;

	@Autowired
	UserController controller;

	private User Anna = new User("token1");
	private User Ben = new User("token2");

	@Test
	public void shouldGetUserCorrectly() {
		assertEquals(repository.save(Ben).getToken(), controller.getUser("token2").getToken());
	}

	@Test(expected = ResponseStatusException.class)
	public void shouldFailToGetUser() {
		repository.save(Ben);
		controller.getUser("token1");
	}

	@Test
	public void shouldAddUserCorrectly() {
		controller.addUserWithToken("token1");
		assertEquals(controller.getUser("token1").getToken(), Anna.getToken());

	}

	@Test(expected = ResponseStatusException.class)
	public void shouldFailGettingUser() {
		controller.addUserWithToken("token1");
		controller.getUser("token2");
	}

	@Test(expected = ResponseStatusException.class)
	public void shouldFailToAddSameUsertwice() {
		controller.addUserWithToken("token1");
		controller.addUserWithToken("token1");
	}

	@Test
	public void shouldUpdateUser() {
		controller.addUserWithToken("token1");
		Anna.setname("Anna");
		controller.updateUser(Anna, "token1");
		assertEquals(Anna.getName(), controller.getUser("token1").getName());
	}

	@Test(expected = ResponseStatusException.class)
	public void shouldDeleteAUserAndFailToGetTheDeletedOne() {
		controller.addUserWithToken("token1");
		controller.addUserWithToken("token2");
		controller.deleteUser("token1");
		controller.getUser("token1");
	}
	
	@Test(expected = ResponseStatusException.class)
	public void shouldTryToDeleteNotExistingUser() {
		controller.addUserWithToken("token1");
		controller.deleteUser("token2");	
	}
	
	@Test(expected = ResponseStatusException.class)
	public void shouldDeleteAllUsersAndFailToGetOne() {
		controller.addUserWithToken("token1");
		controller.addUserWithToken("token2");
		controller.deleteAllUser();
		controller.getUser("token1");
		//controller.getUser("token2");
	}

	@Test
	public void shouldInitaliseAdminUser() {
		controller.initializeAdminUser();
		User a = controller.getUser(adminToken);
		assertNotNull(a);
	}
	
	@Test
	public void shouldTrytoInitalizeTwoAdminUser() {
		controller.initializeAdminUser();
		controller.initializeAdminUser();
		User a = controller.getUser(adminToken);
		assertNotNull(a);
	}
	
		
	@After
	public void tearDown() {
		repository.deleteAll();
	}
}
