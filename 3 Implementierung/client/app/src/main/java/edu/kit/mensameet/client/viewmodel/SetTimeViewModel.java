package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import java.util.Date;

import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;

/**
 * View model of SetTimeActivity.
 */
public class SetTimeViewModel extends MensaMeetViewModel {

    private String startTimeString = "12:00";
    private String endTimeString = "12:00";

    public String getStartTimeString() {
        return startTimeString;
    }

    public void setStartTimeString(String startTimeString) {
        this.startTimeString = startTimeString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public void saveTime() {
        Date startTime;
        Date endTime;

        startTime = MensaMeetTime.stringToTime(startTimeString);
        endTime = MensaMeetTime.stringToTime(endTimeString);

        MensaMeetSession.getInstance().setChosenTime(new MensaMeetTime(startTime, endTime));
    }

    public void saveTimeAndNext() {

        saveTime();

        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.TIME_SAVED_NEXT));
    }

    public void saveTimeAndBack() {

        saveTime();
        eventLiveData.setValue(new Pair<String, StateInterface>(null, State.TIME_SAVED_BACK));
    }

    public enum State implements StateInterface {
        TIME_SAVED_NEXT, TIME_SAVED_BACK, DEFAULT
    }
}
