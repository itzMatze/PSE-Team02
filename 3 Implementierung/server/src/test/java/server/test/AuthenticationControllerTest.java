package server.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.firebase.auth.FirebaseAuthException;

import kit.edu.mensameet.server.controller.AuthenticationController;
import kit.edu.mensameet.server.controller.UserRepository;
import kit.edu.mensameet.server.model.Credentials;
import kit.edu.mensameet.server.model.User;

public class AuthenticationControllerTest {
	@Autowired
	private UserRepository repository;
	Credentials credentials = new Credentials("email@freenet.com", "testing123");
	AuthenticationController controller = new AuthenticationController();
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void test() throws FirebaseAuthException {
		User user = controller.register(credentials);
		assertEquals(user, repository.getUserByToken(user.getToken()));
	}
	
	@After
	public void tearDown() {
		
	}

}
 