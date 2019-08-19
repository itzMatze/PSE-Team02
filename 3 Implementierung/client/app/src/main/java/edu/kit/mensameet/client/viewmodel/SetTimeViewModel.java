package edu.kit.mensameet.client.viewmodel;

import android.util.Pair;

import java.util.Date;
import java.util.List;

import edu.kit.mensameet.client.model.Group;
import edu.kit.mensameet.client.model.Line;
import edu.kit.mensameet.client.model.MensaMeetSession;
import edu.kit.mensameet.client.model.MensaMeetTime;
import edu.kit.mensameet.client.util.RequestUtil;

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

        uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.TIME_SAVED_NEXT));
    }

    public void saveTimeAndBack() {

        saveTime();
        uiEventLiveData.setValue(new Pair<MensaMeetViewModel, StateInterface>(this, State.TIME_SAVED_BACK));
    }

    public enum State implements StateInterface {
        TIME_SAVED_NEXT, TIME_SAVED_BACK, DEFAULT
    }
}
