package server.test;

import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
import kit.edu.mensameet.server.model.Preference;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class GroupControllerTest {
	
	@Autowired
	private GroupRepository repository;
	
	@Autowired
	private GroupController controller;

	private MealLine line = MealLine.CAFETARIA;
	private LocalTime meetingTime; //= LocalTime.of(12, 30);
	private LocalTime startTime = LocalTime.of(12, 00);
	private LocalTime endTime = LocalTime.of(13, 30);
	private MealLine[] lines;
	
	
	/* When creating a group the token of that group is set automatically, 
	 * so that no two groups have the same one
	 */
	private Group testGroup; //= new Group("name", "motto", 2, line, meetingTime ); 
	private Group testGroup2; // = new Group ("name2", "motto2", 4, line, meetingTime);	
	private Preference mypref; 
	/*
	 *  This test verifies that Java.Time.Local works as expected
	 */
	
	@Before 
	public void setUp() {
		lines = new MealLine[2];
		lines[0] = MealLine.LINE_ONE;
		lines [1] = MealLine.LINE_TWO;
		mypref = new Preference(startTime, endTime, lines);
		meetingTime = LocalTime.of(12, 30);
		testGroup = new Group("name", "motto", 2, line, meetingTime); 
		testGroup2 = new Group ("name2", "motto2", 4, line, meetingTime);
	}
	
	
	@Test
	public void meetingTimeWorksCorrectly() {
		meetingTime = LocalTime.of(12, 30);
		assertEquals(meetingTime.toString(), "12:30");
	}
	
	

	
	
	@Test
	public void shouldAddGroupToRepository() {
		
		// testGroup is added to the repository
		Group a = controller.addGroup(testGroup);
		// addGroup should return the Group which was added to the repository
		// so we assert they have the same token 
		assertEquals(testGroup.getToken(), a.getToken());
	}
	
	@Test
	public void shouldGetGroup() {
		//testGroup is added to repository
		Group a = controller.addGroup(testGroup);
		//testGroup should be get from repository 
		Group b = controller.getGroup(a.getToken());
		//the Group we got should be the same we added,
		//so we assert their tokens are the same
		assertEquals(a.getToken(),b.getToken());
	}
	
	 @Test(expected = ResponseStatusException.class)
	 public void shouldFailToGetGroup() {
		 //"testGroup" is not in the repository, so trying to get it should throw an Exception 
		 controller.getGroup(testGroup.getToken());		 
	 }	 
	 
	 /*
	  * Still fails sometimes. Why? 
	  */
	@Test
	public void shouldGetAllGroups() {
		Group a = controller.addGroup(testGroup2);
		Group b = controller.addGroup(testGroup);
		Group[] allGroups = controller.getAllGroups();
		assertTrue(a.getToken().equals(allGroups[0].getToken()) || a.getToken().equals(allGroups[1].getToken()));
		
	}

	@Test
	public void shouldRemoveGroupFromReposiory() {
		// testGroup is added to the repository
		controller.addGroup(testGroup);
		
		//testGroup is removed from the repository
		controller.removeGroup(testGroup.getToken());
		
		//Array allGroups contains all Groups from the repository
		Group[] allGroups = controller.getAllGroups();
		
		//Since the only Group in the repository was removed, 
		//"allgǴroups" should be an empty Array  
		assertEquals(allGroups.length, 0);
		
	}
		
	
	@Test 
	public void shouldRemoveAllGroupsFromRepository() {
		// two groups are added to the repository
		controller.addGroup(testGroup);
		controller.addGroup(testGroup2);
		
		// all groups should be removed from repository
		controller.removeAllGroups();
		
		// the array "allGroups" contains all groups from the repository
		Group[] allGroups = controller.getAllGroups(); 
		
		// since all groups were removed, 
		// "allGroups" should be an empty array
		assertEquals(allGroups.length, 0);
	}
	
	@Test
	public void shouldGetGroupsByPreferenceOfMealline() {
		testGroup.setMealLine(MealLine.LINE_ONE);
		testGroup2.setMealLine(MealLine.CURRY_LINE);
		controller.addGroup(testGroup);
		controller.addGroup(testGroup2);
		//only testGroup should match myprefs
		assertEquals(controller.getGroupByPreferences(mypref).length, 1);
		
	}
	
	@Test 
	public void shouldGetNoGroupByPreference() {
		testGroup.setMealLine(MealLine.CAFETARIA);
		testGroup2.setMealLine(MealLine.CURRY_LINE);
		controller.addGroup(testGroup);
		controller.addGroup(testGroup2);
		//no Group should match myprefs, since the Meallines mismatch
		assertEquals(controller.getGroupByPreferences(mypref).length, 0);
	}

	@Test
	public void shouldGetGroupsByPreferenceOfMeetingTime() {
		testGroup.setMeetingTime(LocalTime.of(10, 00));
		testGroup.setMealLine(MealLine.LINE_ONE);
		testGroup2.setMeetingTime(LocalTime.of(12, 30));
		testGroup2.setMealLine(MealLine.LINE_ONE);
		Group a = controller.addGroup(testGroup);
		Group b = controller.addGroup(testGroup2);
		 
		//Testgroup2 should match, mypref.startTime is 12, endtime 13:30
		assertEquals(controller.getGroupByPreferences(mypref).length, 1);
		assertEquals(controller.getGroupByPreferences(mypref)[0].getToken(), b.getToken());
	}
	
	@After
	public void tearDown() {
		//controller.removeAllGroups();
		repository.deleteAll();
	}
	

}