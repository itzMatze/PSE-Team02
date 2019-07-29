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
	
	/**
	 * Returns current mensadata.
	 * 
	 * @return the mensadata of the current day.
	 * @throws IOException is thrown when the website can't be loaded.
	 */
	@RequestMapping("/mensadata")
	public MensaData getMensaData() throws IOException {
		return mensaDataController.getMensaData();
	}
}
