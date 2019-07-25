package kit.edu.mensameet.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FirebaseAuthentifcator {
	public String encryptToUserToken(String firebaseToken) {
		if (firebaseToken == "xxx") {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Firebase token coulnd't get authenticated.");
		}
		
		return firebaseToken;
	}
}
