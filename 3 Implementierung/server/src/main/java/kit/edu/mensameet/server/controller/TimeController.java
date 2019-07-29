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
	
	/**
	 * is called every monday to friday automatically at midnight and deletes all groups.
	 */
	@Scheduled(cron = "0 0 0 * * MON-FRI")
	public void deleteAllGroups() {
		groupController.removeAllGroups();
	}
	
	/**
	 * Is called every monday to friday at 8 o'clock automatically and updates the mensa data.
	 * 
	 * @throws IOException is thrown when the website can't be loaded.
	 */
	@Scheduled(cron = "0 0 8 * * MON-FRI")
	public void startMensaCrawler() throws IOException {
		mensaDataController.updateMensaData();
	}
}
