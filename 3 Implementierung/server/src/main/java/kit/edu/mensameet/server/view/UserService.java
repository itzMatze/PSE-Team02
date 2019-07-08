package kit.edu.mensameet.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.User;

@RestController
public class UserService {
	
	@Autowired
	private UserController userController;

    @RequestMapping("/getAllUser")
    public User[] user(@RequestParam(value="name", defaultValue="Alice") String name) {
        return userController.getAllUser();
    }
        
    @PostMapping("/user")
    void newUser(@RequestBody User user) throws FirebaseAuthException {
      userController.addUser(user.getName());
      
      CreateRequest request = new CreateRequest()
    		    .setEmail("user@example.com")
    		    .setEmailVerified(false)
    		    .setPassword("secretPassword");

      	
      FirebaseAuth.getInstance().createUser(request);
     
    }
    
    @RequestMapping("/addTestUser")
    User addUser() {
    	User test = new User("hans");
    	test.setToken("test");
    	userController.addUser("testObject");
    	return test; 
    }
}