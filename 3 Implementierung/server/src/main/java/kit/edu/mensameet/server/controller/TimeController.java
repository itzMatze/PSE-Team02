package kit.edu.mensameet.server.controller;

import org.springframework.scheduling.annotation.Scheduled;
import kit.edu.mensameet.server.controller.GroupController;
public class TimeController {
	
	
	@Scheduled(cron = "0 0 0 * * MON-FRI")
	public void deleteAllGroups() {
		
	}
	
	@Scheduled(cron = "0 0 8 * * MON-FRI")
	public void startMensaCrawler() {
			
	}
}
