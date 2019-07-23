package edu.kit.mensameet.client.model;

import java.sql.Time;

public class MensaMeetTime {
    private Time startTime;
    private Time endTime;

    public MensaMeetTime(Time time) {
        startTime = time;
    }

    public MensaMeetTime(Time startTime, Time endTime) {

    }

    public boolean isInterval() {
        return endTime != null;
    }

    public Time getStartTime() {
        return startTime;
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
}