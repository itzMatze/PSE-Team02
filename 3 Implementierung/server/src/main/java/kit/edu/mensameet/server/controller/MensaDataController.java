package kit.edu.mensameet.server.controller;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kit.edu.mensameet.server.model.Line;

@Component
public class MensaDataController {
	
	@Autowired
	private MensaDataRepository repository;
	
	public Line[] getLineData() {
		Line[] lines = StreamSupport.stream(repository.findAll().spliterator(), false).toArray(Line[]::new);
		return lines;
	}
	
	
//	public void updateLineData() {
//		crawler
//	}
	
	
	

}
