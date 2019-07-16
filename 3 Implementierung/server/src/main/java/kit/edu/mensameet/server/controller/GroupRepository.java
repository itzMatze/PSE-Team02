package kit.edu.mensameet.server.controller;

import org.springframework.data.repository.CrudRepository;


import kit.edu.mensameet.server.model.Group;

public interface GroupRepository extends CrudRepository<Group, String>{
	Group getGroupByToken(String Token);
}
