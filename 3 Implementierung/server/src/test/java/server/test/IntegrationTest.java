package server.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalTime;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import kit.edu.mensameet.server.Application;
import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.controller.GroupRepository;
import kit.edu.mensameet.server.controller.MembershipController;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.controller.UserRepository;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.MealLine;
import kit.edu.mensameet.server.model.Preference;
import kit.edu.mensameet.server.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc

public class IntegrationTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private UserController userController;

	@Autowired
	private GroupController groupController;

	@Autowired
	private MembershipController membershipController;

	
	private MealLine line = MealLine.CAFETARIA;
	private LocalTime meetingTime; //= LocalTime.of(12, 30);
	private LocalTime startTime = LocalTime.of(12, 00);
	private LocalTime endTime = LocalTime.of(13, 30);
	private MealLine[] lines;
	
	
	private User amadeus, beethoven, chopin; //= new Group("name", "motto", 2, line, meetingTime ); 
	private Group groupX, groupY; // = new Group ("name2", "motto2", 4, line, meetingTime);	
	private Preference mypref; 
	
	@Before 
	public void setUp() {
		amadeus = new User("amadeus");
		beethoven = new User("beethoven");
		chopin = new User("chopin");
		
		groupX = new Group("groupX", "", 2, line, startTime);
		groupY = new Group("groupY", "", 2, line, startTime);
	}
	
	@Test
	public void scenario1() {
		//Here we simulate the incoming request a client would send when a user creates a group.
		groupController.addGroup(groupX);
		membershipController.addUserToGroup(amadeus, groupX);
		
		//Now the user leaves the group again.
		membershipController.removeUserFromGroup(amadeus, groupX);
		
		//The user now shouldn't be in any group and the group should be deleted.
		assertEquals(amadeus.getGroupToken(), null);
		
		 try {
			assertEquals(groupController.getGroup(groupX.getToken()), null);	
	        fail("Expected an IndexOutOfBoundsException to be thrown");
	    } catch (Exception e) {
	        return;
	    }	
	}
	
	@Test
	public void scenario2() {
		//setup
		groupController.addGroup(groupX);
		membershipController.addUserToGroup(amadeus, groupX);
		membershipController.addUserToGroup(beethoven, groupX);
		
		//beethoven leaves the group to make room for chopin, who is added afterwards
		membershipController.removeUserFromGroup(beethoven, groupX);
		membershipController.addUserToGroup(chopin, groupX);
		
		//Now Amadeus and Chopin should be members of groupX and Beethoven shouldnt be in any group.
		assertTrue(groupX.getMembers().contains(amadeus));
		assertTrue(groupX.getMembers().contains(chopin));
		assertEquals(beethoven.getGroupToken(), null);
	}

	@Test
	public void scenario3() {
		//setup
		groupController.addGroup(groupX);
		membershipController.addUserToGroup(amadeus, groupX);
		
		//Here we simulate the deletion of all groups at midnight.
		groupController.removeAllGroups();
		
		//Amadeus now shouldn't be in any group and the group is should be deleted.
		//Amadeus needs to be refreshed, because the changes are only made on the entity in the repository.
		amadeus = userController.getUser(amadeus.getToken());
		assertEquals(amadeus.getGroupToken(), null);

		try {
			assertEquals(groupController.getGroup(groupX.getToken()), null);	
	        fail("Expected an IndexOutOfBoundsException to be thrown");
	    } catch (Exception e) {
	        return;
	    }	
	}

	@Test
	public void scenario4() {
		//setup
		userRepository.save(amadeus);
		groupController.addGroup(groupX);
		membershipController.addUserToGroup(amadeus, groupX);
		
		//Here we simulate a deletion of amadeus via an admin user.
		System.out.println("now");
		userController.deleteUser(amadeus.getToken());
		
		//Now Amadeus and groupX should be deleted.
		
		assertFalse(userRepository.existsById(amadeus.getToken()));
		assertFalse(groupRepository.existsById(groupX.getToken()));
	}

	
	@After
	public void tearDown() {
		groupController.removeAllGroups();
		userRepository.deleteAll();
		groupRepository.deleteAll();
	}
}
