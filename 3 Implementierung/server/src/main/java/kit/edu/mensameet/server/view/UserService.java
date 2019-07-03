package kit.edu.mensameet.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.User;

@RestController
public class UserService {
	
	@Autowired
	private UserController userController;

    @RequestMapping("/getAllUser")
    public User[] user(@RequestParam(value="name", defaultValue="Alice") String name) {
        return userController.getAllUser();
    }
    
    @RequestMapping("/addUser")
    public String getMessage(@RequestParam(value="name") String name) {
    	userController.addUser(name);
        return "Added User!";
    }
}