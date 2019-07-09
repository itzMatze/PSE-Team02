package kit.edu.mensameet.server.model;

import java.time.*;

public class Preference {
	
	private LocalTime startTime;
	private LocalTime endTime;
	private MealLine[] mealLines;
	
	public LocalTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public MealLine[] getMealLines() {
		return mealLines;
	}
	
	public void setMealLines(MealLine[] mealLines) {
		this.mealLines = mealLines;
	}
	

}
