package kit.edu.mensameet.server.view;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kit.edu.mensameet.server.controller.MensaDataController;
import kit.edu.mensameet.server.model.MensaData;

@RestController
public class MensalineService {

	@Autowired
	private MensaDataController mensaDataController;
	
	@RequestMapping("/mensadata")
	public MensaData getMensaData() throws IOException {
		return mensaDataController.getMensaData();
	}
}
