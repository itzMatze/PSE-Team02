package kit.edu.mensameet.server.view;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kit.edu.mensameet.server.controller.UserController;
import kit.edu.mensameet.server.model.User;

@RestController
public class UserService {
	
	private UserController userController;

    @RequestMapping("/getAllUser")
    public User[] user(@RequestParam(value="name", defaultValue="Alice") String name) {
        return userController.getAllUser();
    }
}