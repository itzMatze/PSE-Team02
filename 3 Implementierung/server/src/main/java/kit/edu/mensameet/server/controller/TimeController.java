package kit.edu.mensameet.server.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import kit.edu.mensameet.server.controller.GroupController;
public class TimeController {
	
	@Autowired
	private GroupController groupController;
	
	@Autowired
	private MensaDataController mensaDataController;
	
	@Scheduled(cron = "0 0 0 * * MON-FRI")
	public void deleteAllGroups() {
		groupController.removeAllGroups();
	}
	
	@Scheduled(cron = "0 0 8 * * MON-FRI")
	public void startMensaCrawler() throws IOException {
			mensaDataController.updateMensaData();
	}
}
