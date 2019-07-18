package kit.edu.mensameet.server.controller;


import org.springframework.data.repository.CrudRepository;

import kit.edu.mensameet.server.model.Line;


public interface MensaDataRepository extends CrudRepository<Line, String>{
	Line getLineByName(String MealLine);
	

}
