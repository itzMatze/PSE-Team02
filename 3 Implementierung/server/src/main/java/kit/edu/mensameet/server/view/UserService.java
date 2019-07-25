package kit.edu.mensameet.server.view;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import javassist.NotFoundException;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.User;

@RestController
public class UserService {
	
	@Autowired
	private UserController userController;

	/*------------ this is only for testing purposes.
    @RequestMapping("/getAllUser")
    public User[] user(@RequestParam(value="name", defaultValue="Alice") String name) {
        return userController.getAllUser();
    }
    */

	
	@RequestMapping("/user") 
	public User	getUserByToken(@RequestHeader(value="token") String token) {
		return userController.getUser(token);
	}
	
    @PostMapping("/user")
    void createUser(@RequestHeader(value="token") String token) throws FirebaseAuthException {
    	userController.addUserWithToken(token);
    }
    
    @PutMapping("/user") 
    public void updateUser(@RequestBody User user, @RequestHeader(value = "firebaseToken") String firebaseToken) throws NotFoundException {
    	userController.updateUser(user, firebaseToken);
    }
    
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam(value="token") String token) {
    	userController.deleteUser(token);
    }
}