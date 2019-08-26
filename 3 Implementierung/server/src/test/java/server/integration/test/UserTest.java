package server.integration.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.controller.GroupRepository;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.MealLine;
import kit.edu.mensameet.server.model.Preference;

public class UserTest {
	
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
	
	@After
	public void tearDown() {
		//controller.removeAllGroups();
		repository.deleteAll();
	}
}
