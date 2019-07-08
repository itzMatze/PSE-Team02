package kit.edu.mensameet.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import kit.edu.mensameet.server.controller.AuthenticationController;
import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.Credentials;
import kit.edu.mensameet.server.model.User;

@RestController
public class AccountService {

	@Autowired
	private AuthenticationController authenticationController;

    @PostMapping("/register")
    User register(@RequestBody Credentials credentials) throws FirebaseAuthException {
      return authenticationController.register(credentials);
    }

    @PostMapping("/login")
    User login(@RequestBody Credentials credentials) throws FirebaseAuthException {
      return authenticationController.register(credentials);
    }
}
