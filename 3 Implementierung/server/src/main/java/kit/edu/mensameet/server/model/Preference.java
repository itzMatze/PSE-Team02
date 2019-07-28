package kit.edu.mensameet.server.model;

import java.time.*;

/**
 * This class represents the chosen preferences of a user, based on time and
 * meal lines
 *
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

	public Preference() {
	}

	/**
	 * 
	 * @return the starttime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * 
	 * @param startTime is the start time of an interval a user wants to go to eat
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * 
	 * @return the endtime of the intervall
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * 
	 * @param endTime is the end time of the interval a user wants to go to eat
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * 
	 * @return an array of all meal lines the user wants to go to eat
	 */
	public MealLine[] getMealLines() {
		return mealLines;
	}

	/**
	 * 
	 * @param mealLines is an array of all meal lines
	 */
	public void setMealLines(MealLine[] mealLines) {
		this.mealLines = mealLines;
	}
}