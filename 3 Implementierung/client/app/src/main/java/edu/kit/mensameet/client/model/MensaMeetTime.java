package edu.kit.mensameet.client.model;

//nicht festgelegt
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

    @Override
    public String toString(){
        if (startTime == null){
            return "0";
        }
        if(isInterval()){
            return startTime.toString() + " - " + endTime.toString();
        }
        return startTime.toString();
    }
}