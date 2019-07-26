package kit.edu.mensameet.server.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import kit.edu.mensameet.server.model.Group;

@Repository
public interface GroupRepository extends CrudRepository<Group, String>{
	Group getGroupByToken(String token);
}
