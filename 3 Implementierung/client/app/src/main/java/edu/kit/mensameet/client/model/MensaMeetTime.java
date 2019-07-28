package edu.kit.mensameet.client.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public MensaMeetTime(Date time) {
        startTime = time;
    }

    public MensaMeetTime(Date startTime, Date endTime) {
        setStartTime(startTime);
        setEndTime(endTime);
    }

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

    private static SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:mm:yyyy");

    public static Date stringToTime(String timeString) {
        if (timeString == null) return null;
        try {
            return simpleTimeFormat.parse(timeString);
        } catch (ParseException e) {

            return null;
        }
    }

    public static String timeToString(Date time) {
        if (time == null) return null;
        return simpleTimeFormat.format(time);
    }

    public static Date stringToDate(String dateString) {
        if (dateString == null) return null;
        try {
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {

            return null;
        }
    }

    public static String dateToString(Date time) {
        if (time == null) return null;
        return simpleDateFormat.format(time);
    }
}