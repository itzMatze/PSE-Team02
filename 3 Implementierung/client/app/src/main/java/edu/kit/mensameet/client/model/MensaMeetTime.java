package edu.kit.mensameet.client.model;

import java.sql.Time;

public class MensaMeetTime {
    private Time startTime;
    private Time endTime;

    public MensaMeetTime(Time time) {

    }

    public MensaMeetTime(Time startTime, Time endTime) {

    }

    public boolean isInterval() {
        return true;
    }
}
