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

import kit.edu.mensameet.server.controller.GroupController;
import kit.edu.mensameet.server.model.Group;
import kit.edu.mensameet.server.model.Preference;

@RestController
public class GroupService {
	
	@Autowired
	private GroupController groupController;
	
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
    public void deleteGroup(@RequestHeader(value="token") String token, @RequestParam(value="token") String groupToken) {
    	groupController.removeGroup(groupToken);
    }
}
