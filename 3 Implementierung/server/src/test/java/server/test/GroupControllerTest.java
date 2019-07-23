package server.test;

import static org.junit.Assert.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.controller.GroupRepository;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.Line;

@RunWith(SpringRunner.class)
@DataJpaTest
public class GroupControllerTest {
	@Autowired
	private GroupRepository repository;
	@Autowired
	private GroupController controller;
	

	private Line line = new Line();
	private LocalTime meetingTime = LocalTime.of(12, 30);
	
	private Group testGroup = new Group("token", "name", "motto", 2, line, meetingTime ); 

	@Test
	public void meetingTimeNotNull() {	
			assertEquals(meetingTime.toString(), "12:30");
	}

	
	@Test
	public void groupAddedCorrectly() {
		Group a = repository.save(testGroup);
		System.out.println(a.toString());
		assertEquals(a, testGroup);
	}
	

}