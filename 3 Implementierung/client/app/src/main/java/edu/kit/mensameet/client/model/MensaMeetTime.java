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

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public static Date stringToDate(String timeString) {
        if (timeString == null) return null;
        try {
            return simpleDateFormat.parse(timeString);
        } catch (ParseException e){
            // TODO: Fehlerbehandlung
            return null;
        }
    }

    public static String dateToString(Date date) {
        if (date == null) return null;
        return simpleDateFormat.format(date);
    }
}