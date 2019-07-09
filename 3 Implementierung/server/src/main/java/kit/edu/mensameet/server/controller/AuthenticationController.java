package kit.edu.mensameet.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import kit.edu.mensameet.server.model.Credentials;
import kit.edu.mensameet.server.model.User;

@Component
public class AuthenticationController {
	
	@Autowired
	UserRepository userRepository;
	
	public User register(Credentials credentials) throws FirebaseAuthException {
		CreateRequest request = new CreateRequest()
    		    .setEmail(credentials.getEmail())
    		    .setEmailVerified(false)
    		    .setPassword(credentials.getPassword());

      	
		//Send request to Firebase server and receive newly created user
		UserRecord userRecord =  FirebaseAuth.getInstance().createUser(request);
		
		User newUser = new User(userRecord.getUid());
		userRepository.save(newUser);
		
		return newUser;
	}
	
	public User login(Credentials credentials) throws FirebaseAuthException {
		CreateRequest request = new CreateRequest()
    		    .setEmail(credentials.getEmail())
    		    .setEmailVerified(false)
    		    .setPassword(credentials.getPassword());

      	
		//Send request to Firebase server and receive newly created user
		//UserRecord userRecord =  FirebaseAuth.getInstance().createUser(request);
		UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(credentials.getEmail());
		FirebaseAuth.getInstance();

		User newUser = new User(userRecord.getUid());
		
		
		return newUser;
	}
}
