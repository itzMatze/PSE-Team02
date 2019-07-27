package kit.edu.mensameet.server.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

@Component
public class FirebaseAuthentifcator {
	
	@Value("${production.mode}")
	private boolean productionMode; 
	
	public String authenticateAndEncryptFirebaseTokenToUserToken(String firebaseToken) {
		String uid ;
		
		if (productionMode) {			
			try {
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);				
				uid = decodedToken.getUid();
			} catch (FirebaseAuthException e) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Firebase token coulnd't be authenticated.");
			}

			return uid;
		} else {
			return firebaseToken;
		}
	}
	
	public void authenticateFirebaseToken(String firebaseToken) {
		try {
			FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
		} catch (FirebaseAuthException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Firebase token coulnd't be authenticated.");
		}
	}
}
