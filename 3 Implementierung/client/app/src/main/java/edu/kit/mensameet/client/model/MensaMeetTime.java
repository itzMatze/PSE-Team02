package edu.kit.mensameet.client.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Time class for mensa meet, containing start end end time for the interval a user wants to go eating.
 */
public class MensaMeetTime {
    private Date startTime;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        if (startTime.compareTo(endTime) <= 0) {
            this.endTime = endTime;
        } else {
            this.endTime = startTime;
        }
    }

    private Date endTime;

    /**
     * Constructor.
     *
     * @param time One time.
     */
    public MensaMeetTime(Date time) {
        startTime = time;
    }

    /**
     * Constructor.
     *
     * @param startTime Start time.
     * @param endTime End time.
     */
    public MensaMeetTime(Date startTime, Date endTime) {
       setStartTime(startTime);
       setEndTime(endTime);
    }

    /**
     * Whether the time information is an interval.
     *
     * @return
     */
    public boolean isInterval() {
        return !(startTime.compareTo(endTime) == 0);
    }

    /*
    for testing
    todo: delete, ask question why
     */

    @Override
    public String toString() {
        if (startTime == null) {
            return "0";
        }
        if (isInterval()) {
            return startTime.toString() + " - " + endTime.toString();
        }
        return startTime.toString();
    }

    /**
     * Format string for a time.
     */
    private static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");

    /**
     * Format string for a date.
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.mm.yyyy");

    /**
     * Auxiliary method that converts a time string to a Date object.
     *
     * @param timeString Time string.
     * @return Date object.
     */
    public static Date stringToTime(String timeString) {
        if (timeString == null) return null;
        try {
            return simpleTimeFormat.parse(timeString);
        } catch (ParseException e){

            return null;
        }
    }

    /**
     * Converts a Date object into a time string.
     *
     * @param time Date object.
     * @return Time string.
     */
    public static String timeToString(Date time) {
        if (time == null) return null;
        return simpleTimeFormat.format(time);
    }

    /**
     * Converts a date string to a Date object.
     *
     * @param dateString Date string.
     * @return Date object.
     */
    public static Date stringToDate(String dateString) {
        if (dateString == null) return null;
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e){

            return null;
        }
    }

    /**
     * Converts a Date object to a date string.
     *
     * @param date Date object.
     * @return date string.
     */
    public static String dateToString(Date date) {
        if (date == null) return null;
        return simpleDateFormat.format(date);
    }
}