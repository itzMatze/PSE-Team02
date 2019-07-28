package kit.edu.mensameet.server.view;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	
	/**
	 * This method returns a user identified by the userToken.
	 * 
	 * @param firebaseToken the token of the session to verify the request.
	 * @param userToken the token of the requested user
	 * @return returns the user indetified by the userToken
	 */
	@RequestMapping("/user") 
	public User	getUserByToken(@RequestHeader(value = "firebaseToken")String firebaseToken, 
							   @RequestHeader(value="userToken") String userToken) {
		fbAuth.authenticateFirebaseToken(firebaseToken);
		return userController.getUser(userToken);
	}

	/**
	 * Creates a user with the id encrypted from the firebaseToken.
	 * 
	 * @param firebaseToken firebaseToken the token of the session to verify the request.
	 * 		  Also the user is encrypted from the token.
	 */
    @PostMapping("/user")
    void createUser(@RequestHeader(value="firebaseToken") String firebaseToken) {
    	String userToken = fbAuth.authenticateAndEncryptFirebaseTokenToUserToken(firebaseToken);
    	userController.addUserWithToken(userToken);
    }
    
    /**
     * Updates a user in the database.
     * 
     * @param user the user information that should be saved.
     * @param firebaseToken for verifying the request and to be encrypted to the user token.
     */
    @PutMapping("/user") 
    public void updateUser(@RequestBody User user, @RequestHeader(value = "firebaseToken") String firebaseToken) {
    	String userToken = fbAuth.authenticateAndEncryptFirebaseTokenToUserToken(firebaseToken);
    	userController.updateUser(user, userToken);
    }
    
    /**
     * Deletes a user from the database.
     * 
     * @param firebaseToken for verifying the request.
     * @param userToken of the user that should be deleted.
     */
    @DeleteMapping("/user")
    public void deleteUser(@RequestParam(value="firebaseToken") String firebaseToken,
    					   @RequestParam(value="userToken") String userToken) {
    	String requestingUserToken = fbAuth.authenticateAndEncryptFirebaseTokenToUserToken(firebaseToken);
    	
		if (!userController.getUser(requestingUserToken).getIsAdmin()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User with token " + userToken + " has no permission to do this action."); 
		}

    	
    	userController.deleteUser(userToken);
    }
}