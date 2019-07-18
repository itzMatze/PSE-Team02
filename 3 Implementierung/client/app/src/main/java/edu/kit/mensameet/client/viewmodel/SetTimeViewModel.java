package edu.kit.mensameet.client.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Pair;

import java.sql.Time;
import edu.kit.mensameet.client.model.MensaMeetTime;

public class SetTimeViewModel extends MensaMeetViewModel {

    private MutableLiveData<Integer> hour;
    private MutableLiveData<Integer> minute;
    private MutableLiveData<MensaMeetTime> time;
    private SingleLiveEvent<Pair<SetTimeViewModel, String>> uiEventLiveData;

    /*
     this method is for testing saveTime method
     todo: delete if it's no longer needed or added to changes
     */

    /**
     * @return time
     */
    public LiveData<MensaMeetTime> getTime() {
        if(time == null){
            time = new MutableLiveData<>();
            time.setValue(new MensaMeetTime(new Time(0)));
        }
        return time;
    }

    /**
     * @return a string representing start time in format HH:MM:SS
     */
    public String getTimeString(){
        return time.getValue().getStartTime().toString();
    }

    /**
     * @param time
     */
    public void setTime(LiveData<MensaMeetTime> time) {
        this.time = (MutableLiveData)time;
    }


    /**
     * save time in the given view model using stored hour and minute
     * @param item view model
     */
    public void onSaveTimeClick(SetTimeViewModel item) {
        time.setValue(new MensaMeetTime(new Time(hour.getValue(), minute.getValue(),0)));
        uiEventLiveData.setValue(new Pair<>(item, "saveTime"));
    }

    /**
     * @return hour
     */
    public MutableLiveData<Integer>  getHour(){
        if (hour == null){
            hour = new MutableLiveData<>();
            hour.setValue(0);
        }
        return hour;
    }

    /**
     * @return minute
     */
    public MutableLiveData<Integer>  getMinute() {
        if (minute == null){
            minute = new MutableLiveData<>();
            minute.setValue(0);
        }
        return minute;
    }

    /**
     * @return UI event
     */
    public SingleLiveEvent<Pair<SetTimeViewModel, String>> getUiEventLiveData() {
        if(uiEventLiveData == null){
            uiEventLiveData = new SingleLiveEvent<>();
            uiEventLiveData.setValue(new Pair<SetTimeViewModel, String>(null, "default"));
        }
        return uiEventLiveData;
    }
}
