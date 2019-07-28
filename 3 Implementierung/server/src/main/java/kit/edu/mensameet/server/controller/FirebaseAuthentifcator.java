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
	
	/**
	 * Authenticates a firebase token and encrypts it to the user token.
	 * 
	 * @param firebaseToken  for verifying the request and to be encrypted to the user token.
	 * @return the userToken.
	 */
	public String authenticateAndEncryptFirebaseTokenToUserToken(String firebaseToken) {
		String userToken;
		
		if (productionMode) {			
			try {
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);				
				userToken = decodedToken.getUid();
			} catch (FirebaseAuthException e) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Firebase token coulnd't be authenticated.");
			}

			return userToken;
		} else {
			return firebaseToken;
		}
	}
	
	/**
	 * Authenticates a firebaseToken.
	 * 
	 * @param firebaseToken to be authenticated.
	 */
	public void authenticateFirebaseToken(String firebaseToken) {
		if (productionMode) {
			try {
				FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
			} catch (FirebaseAuthException e) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Firebase token coulnd't be authenticated.");
			}			
		}
	}
}
