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
	
	public String encryptToUserToken(String firebaseToken) {
		String uid ;
		
		if (productionMode) {			
			if (firebaseToken == "xxx") {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Firebase token coulnd't get authenticated.");
			}
			
			try {
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);				
				uid = decodedToken.getUid();
			} catch (FirebaseAuthException e) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Coud't authenticate firebase token.");
			}

			return uid;
		} else {
			return firebaseToken;
		}
	}
}
