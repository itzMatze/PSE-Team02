package server.test;

import static org.junit.Assert.*;

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
	UserController userController;

	@Autowired
	MembershipController controller;
	
	@Before
	public void setUp() {
				
		users = new User[3];
		users[0] = new User("Anna");
		users[1] = new User("Beate");
		users[2] = new User("Cedric");
		
		//users need to be saved be listed in a group
		
		
		group = new Group("token", "name", "motto", 2, MealLine.CAFETARIA, null); 
	}
	
	@Test
	public void addUserWhenGroupEmpty() {
		controller.addUserToGroup(users[0], group);
		assertEquals(group.getMembers()[0], users[0]);
	}
	
	@Test(expected = ResponseStatusException.class /* no exception expected */)
	public void addUserWhenGroupIsfFull() {
		controller.addUserToGroup(users[0], group);
		controller.addUserToGroup(users[1], group);
		
		controller.addUserToGroup(users[2], group);
	}
	
	@Test
	public void removeLastUserFromGroup() {
		controller.addUserToGroup(users[0], group);
		assertEquals(controller.removeUserFromGroup(users[0], group), true);
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
		User[] groupcontent = group.getMembers();
		//System.out.println(groupcontent[0].getToken());
		//System.out.println(groupcontent[1].getToken());
		controller.removeUserFromGroup(users[0], group);
		System.out.println(group.getMembers());
		assertEquals(group.getMembers()[0], users[1]);
		
		/*
		 * for (int i = 0; i < group.getMaxMembers(); i++) {
			if (group.getMembers()[i].getToken() == user.getToken()) {
				group.getMembers()[i] = group.getMembers()[group.getCurrentMembers()];
				group.getMembers()[group.getCurrentMembers()] = null;
				group.setCurrentMembers(group.getCurrentMembers() - 1);
				return true;
			}
		}
		return false;
	}
		 */
	}
	
	@After
	public void tearDown() {
		users = null;
		group = null;
		controller = null;
	}
}
