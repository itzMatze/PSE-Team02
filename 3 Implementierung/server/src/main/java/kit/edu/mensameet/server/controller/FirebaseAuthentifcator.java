package kit.edu.mensameet.server.controller;

import org.springframework.stereotype.Component;

@Component
public class FirebaseAuthentifcator {
	public String encryptToUserToken(String firebaseToken) {
		return firebaseToken;
	}
}
