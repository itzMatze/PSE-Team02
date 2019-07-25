package server.test;

import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalTime;

import org.junit.After;
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

	@Test
	public void meetingTimeNotNull() {	
			assertEquals(meetingTime.toString(), "12:30");
	}

	
	@Test
	public void groupAddedCorrectlyToRepository() {
		Group a = controller.addGroup(testGroup);
		assertEquals(testGroup.getToken(), a.getToken());
	}
	
	@Test
	public void removeGroup() {
		controller.addGroup(testGroup);
		controller.removeGroup("token");
		Group[] groups = controller.getAllGroups();
		assertNotEquals(groups[0].getToken(), testGroup.getToken());
	}
	
	@After
	public void tearDown() {
		testGroup = null;
		controller.removeAllGroups();
	}

}