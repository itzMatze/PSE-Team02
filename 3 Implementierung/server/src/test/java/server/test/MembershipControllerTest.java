package server.test;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Before;

import org.junit.After;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import kit.edu.mensameet.server.Application;
import kit.edu.mensameet.server.controller.GroupRepository;
import kit.edu.mensameet.server.controller.MembershipController;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.Line;
import kit.edu.mensameet.server.model.MealLine;
import kit.edu.mensameet.server.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MembershipControllerTest {

	User[] users;
	Group group;

	@Autowired
	MembershipController controller;
	
	@Autowired
	GroupRepository repository;
	
	private LocalTime meetingTime = LocalTime.of(12, 30);
	
	@Before
	public void setUp() {
				
		users = new User[3];
		users[0] = new User("Anna");
		users[1] = new User("Beate");
		users[2] = new User("Cedric");
		
		group = new Group("token", "name", "motto", 2, MealLine.CAFETARIA, meetingTime); 
	}
	
	@Test
	public void addUserWhenGroupEmpty() {
		controller.addUserToGroup(users[0], group);
		assertEquals(group.getMembers().indexOf(0), users[0]);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void addUserWhenGroupIsfFull() {
		controller.addUserToGroup(users[0], group);
		controller.addUserToGroup(users[1], group);
		
		controller.addUserToGroup(users[2], group);
	}
	
	@Test
	public void removeLastUserFromGroup() {
		controller.addUserToGroup(users[0], group);
		controller.removeUserFromGroup(users[0], group);
	}
	
//	@Test
//	public void removeUserWhoIsNotInGroup() {
//		controller.addUserToGroup(users[0], group);
//		assertEquals(controller.removeUserFromGroup(users[1], group), false);
//	}
	
	@Test 
	public void removeUser() {
		controller.addUserToGroup(users[0], group);
		controller.addUserToGroup(users[1], group);
		controller.removeUserFromGroup(users[0], group);
		System.out.println(group.getMembers());
		assertEquals(group.getMembers().get(0), users[1]);
	}
	
	@After
	public void tearDown() {
		users = null;
		group = null;
		controller = null;
	}
}
