package kit.edu.mensameet.server.view;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.auth.FirebaseAuthException;

import kit.edu.mensameet.server.controller.FirebaseAuthentifcator;
import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.MealLine;
import kit.edu.mensameet.server.model.Preference;

@RestController
public class GroupService {
	
	@Autowired
	private GroupController groupController;
	
	
	@Autowired
	private FirebaseAuthentifcator fbAuthentificator;
	
	@RequestMapping("/group") 
	public Group getGroupByToken(@RequestHeader(value="token") String token, @RequestParam(value="groupToken") String groupToken) {
		return groupController.getGroup(groupToken);
	}
	
	@RequestMapping("/time")
	public LocalTime getGroupByToken() {
		return LocalTime.now();
	}
	
    @PostMapping("/group-prefferences")
    Group[] getGroupsByPrefferences(@RequestHeader(value="token") String token, @RequestBody Preference prefs) throws FirebaseAuthException {
    	//FirebaseAuth.getInstance().verifyIdToken(token);
    	
    	return groupController.getGroupByPreferences(prefs);
    }
    
    @PostMapping("/create-group") 
    public Group createGroup(@RequestHeader(value="token") String token, @RequestBody Group group) {
    	return groupController.addGroup(group);
    }
    
    @DeleteMapping("/group")
    public void deleteGroup(@RequestHeader(value="token") String token, @RequestParam(value="groupToken") String groupToken) {
    	String userToken = fbAuthentificator.encryptToUserToken(token);
    	
    	//check if user with @userToken has admin permissions.
    	
    	groupController.removeGroup(groupToken);
    }
    
    @RequestMapping("/prefs")
    public Preference getPrefs() {
    	MealLine[] lines;
    	lines = new MealLine[3];
    	lines[0] = MealLine.LINE_SIX;
    	lines[1] = MealLine.CAFETARIA_LATE;
    	lines[2] = MealLine.LINE_FOUR_FIVE;
    	return new Preference(LocalTime.of(11, 30), LocalTime.of(13, 0), lines);
    }
    
}
