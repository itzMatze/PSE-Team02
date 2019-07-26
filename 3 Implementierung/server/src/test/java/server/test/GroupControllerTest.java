package server.test;

import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalTime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import kit.edu.mensameet.server.Application;
import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.controller.GroupRepository;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.Line;
import kit.edu.mensameet.server.model.MealLine;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class GroupControllerTest {
	
	@Autowired
	private GroupRepository repository;
	
	@Autowired
	private GroupController controller;

	private MealLine line = MealLine.CAFETARIA;
	private LocalTime meetingTime = LocalTime.of(12, 30);
	private Group testGroup = new Group("token", "name", "motto", 2, line, meetingTime ); 
	private Group testGroup2 = new Group ("token2", "name2", "motto2", 4, line, meetingTime);	
	@Test
	public void meetingTimeWorksCorrectly() {	
			assertEquals(meetingTime.toString(), "12:30");
	}

	
	@Test
	public void groupAddedCorrectlyToRepository() {
		Group a = controller.addGroup(testGroup);
		assertEquals(testGroup.getToken(), a.getToken());
	}
	/*
	 * This test fails; 
	 * repository seems to still contain "testGroup" after using the method removeGroup
	 * The next test "test" shows something's wrong with the method getGroupByToken
	 */
	@Test
	public void GroupRemovedCorrectlyFromRepository() {
		controller.addGroup(testGroup);
		controller.removeGroup("token");
		Group[] allGroups = controller.getAllGroups();
		assertEquals(allGroups.length, 0);
		//assertNotEquals(allGroups[0].getToken(), testGroup.getToken());

	}

	@Test
	public void test() {
		//controller.addGroup(testGroup2);
		repository.save(testGroup2);
		Group a = repository.getGroupByToken("token2");
		assertEquals(a.getToken(), "token2");
		
	}
	
	@Test 
	public void AllGroupsRemovedCorrectlyFromRepository() {
		controller.addGroup(testGroup);
		controller.addGroup(testGroup2);
		controller.removeAllGroups();
		Group[] noGroups = controller.getAllGroups(); 
		assertEquals(noGroups.length, 0);
	}
	

	
	@After
	public void tearDown() {
		controller.removeAllGroups();
	}
	

}