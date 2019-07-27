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
import kit.edu.mensameet.server.controller.FirebaseAuthentifcator;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.User;

@RestController
public class UserService {
	
	@Autowired
	private UserController userController;

	@Autowired
	private FirebaseAuthentifcator fbAuth;
	
	@RequestMapping("/user") 
	public User	getUserByToken(@RequestHeader(value = "firebaseToken")String firebaseToken, 
							   @RequestHeader(value="userToken") String userToken) {
		//it only gets checked wether the request comes from an logged in user
		fbAuth.encryptToUserToken(firebaseToken);
		return userController.getUser(userToken);
	}
	
    @PostMapping("/user")
    void createUser(@RequestHeader(value="firebaseToken") String firebaseToken) {
    	String userToken = fbAuth.encryptToUserToken(firebaseToken);
    	userController.addUserWithToken(userToken);
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