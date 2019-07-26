package kit.edu.mensameet.server.model;

import java.time.*;
/*
 * This class represents the chosen preferences of a user, based on time and meal lines
 */
public class Preference {
	
	private LocalTime startTime;
	private LocalTime endTime;
	private MealLine[] mealLines;
	
	
	public Preference(LocalTime startTime, LocalTime endTime, MealLine[] lines) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.mealLines = lines;
	}
	
	public Preference() {}
	
	/*
	 * default getter for the starting time of an interval in which the user wants to eat
	 */
	public LocalTime getStartTime() {
		return startTime;
	}
	/*
	 * default setter for the starting time of an interval in which the user wants to eat
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	/*
	 * default getter for the ending time of an interval in which the user wants to eat
	 */
	public LocalTime getEndTime() {
		return endTime;
	}
	/*
	 * default getter for the ending time of an interval in which the user wants to eat
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	/*
	 * default getter for the chosen meal lines
	 */
	public MealLine[] getMealLines() {
		return mealLines;
	}
	/*
	 * default setter for the chosen meal lines
	 */
	public void setMealLines(MealLine[] mealLines) {
		this.mealLines = mealLines;
	}
}