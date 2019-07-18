package kit.edu.mensameet.server.controller;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kit.edu.mensameet.server.model.Line;
import kit.edu.mensameet.server.model.MensaData;

@Component
public class MensaDataController {
	
	private MensaData mensaData;
	
	public MensaData getMensaData() {
		if (mensaData == null) {
			updateMensaData();
		}
		
		return mensaData;
	}
	
	
	public void updateMensaData() {
		//crawl mensaData
	}
}
